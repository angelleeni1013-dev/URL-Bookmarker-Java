package URLBookmark;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.awt.Desktop;
import java.io.File;

public class Controller implements ActionListener{
    
    public final static String URLBOOKMARK_HTML_PATH = "URLBookmark.html";
    private Provider provider;
    private FrameUI frameUI;
    private HomePageUI homePageUI;  
    private AddURLPageUI addURLPageUI;
    private EditURLPageUI editURLPageUI;

    //constructor
    public Controller(){
        provider = new Provider();
        if (!provider.readFile()) {
            System.out.println("Error: Failed to load or parse data from file.");
            JOptionPane.showMessageDialog(null,
                                        "Failed to load or parse data from the file. Please check the file content and try again.",
                                        "File Error",
                                        JOptionPane.WARNING_MESSAGE); 
        }

        frameUI = new FrameUI();
        
        //HomePageUI 
        homePageUI = new HomePageUI();
        //Set HomePageUI Button ActionListener
        homePageUI.getJBTSearch().addActionListener(this);
        RecordUI[] recordUIs = homePageUI.getRecordUIs();
        for(int i = 0; i < recordUIs.length; i++){
            recordUIs[i].getJBTURL().addActionListener(this);
            recordUIs[i].getJBTDelete().addActionListener(this);
        }
        homePageUI.getJBTAdd().addActionListener(this);
        homePageUI.getJBTExport().addActionListener(this);
        homePageUI.getJBTFirst().addActionListener(this);
        homePageUI.getJBTPrevious().addActionListener(this);
        homePageUI.getJBTNext().addActionListener(this);
        homePageUI.getJBTLast().addActionListener(this);
        
        //AddURLPageUI
        addURLPageUI = new AddURLPageUI();
        //Set AddURLPageUI Button ActionListener
        addURLPageUI.getJBTAdd().addActionListener(this);
        addURLPageUI.getJBTExit().addActionListener(this);
        
        //EditURLPageUI
        editURLPageUI = new EditURLPageUI();
        //Set EditURLPageUI Button ActionListener
        editURLPageUI.getJBTEdit().addActionListener(this);
        editURLPageUI.getJBTExit().addActionListener(this);
        
        //set starting page to homePageUI
        //by change frameUI panel
        //setup homePageUI data before use
        homePageUI.setURLs(provider.select());
        homePageUI.displayALLData();
        frameUI.setPanel(homePageUI.getPanel());
        frameUI.refrashPanel();
    }//Controller
    
    @Override
    public void actionPerformed(ActionEvent e){
        //homePageUI
        if(e.getSource() == homePageUI.getJBTSearch()){
            searchURL();//select record by user input(title/category)
            
            //display first 5 record
            homePageUI.setPage(0);
            homePageUI.displayALLData();
        }
        
        RecordUI[] recordUIs = homePageUI.getRecordUIs();
        for(int i = 0; i < recordUIs.length; i++){
            if(e.getSource() == recordUIs[i].getJBTURL()){
                //setup editURLPageUI data
                editURLPageUI.setURL(recordUIs[i].getURL());
                editURLPageUI.diaplayAllData();
                
                homePageUI.clearALLData();//clear dirty data homePageUI after give to editURLPageUI
                
                //goto editURLPageUI
                frameUI.setPanel(editURLPageUI.getPanel());
                frameUI.refrashPanel();
            }//if
        }//for
        
        for(int i = 0; i < recordUIs.length; i++){
            if(e.getSource() == recordUIs[i].getJBTDelete()){
                int choice = JOptionPane.showConfirmDialog(null, "Confirm Delete?", "Delete Confirmation", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION){
                    if(!provider.delete(recordUIs[i].getURL().getTitle()))// Failed to delete the record
                        JOptionPane.showMessageDialog(null, 
                                                    "Failed to delete the URL bookmark.",
                                                    "Delete Error",
                                                    JOptionPane.WARNING_MESSAGE);
                    else{
                        if(!provider.writeFile()){
                            // If writing to the file fails after deletion, re-insert the deleted data to restore original state
                            JOptionPane.showMessageDialog(null,
                                                        "Failed to complete deletion. The URL bookmark has been restored.",
                                                        "Delete Error",
                                                        JOptionPane.WARNING_MESSAGE);
                            provider.insert(Validation.getCategoryIndex(recordUIs[i].getURL().getCategory()), recordUIs[i].getURL());
                            }//if
                        else{
                            searchURL();
                            if(!homePageUI.isValidPage(homePageUI.getPage())){
                                if(homePageUI.getURLs().isEmpty())
                                    homePageUI.setPage(0);
                                else
                                    homePageUI.setPage((homePageUI.getURLs().size() - 1) / homePageUI.getRecordUIs().length);//goto last page
                                }//if
                            homePageUI.displayALLData();  
                            JOptionPane.showMessageDialog(null, 
                                                        "URL bookmark deleted successfully.",
                                                        "Delete Successful",
                                                        JOptionPane.INFORMATION_MESSAGE);
                        }//else
                    }//else
                }//if
            }//if
        }//for
        
        //move to first page
        if(e.getSource() == homePageUI.getJBTFirst()){
            homePageUI.setPage(0);
            homePageUI.displayALLData();//display new page
        }
        
        //move to previous page
        if(e.getSource() == homePageUI.getJBTPrevious()){
            if(homePageUI.isValidPage(homePageUI.getPage() - 1)){
                homePageUI.setPage(homePageUI.getPage() - 1);
                homePageUI.displayALLData();//display new page
            }
        }
        
        //move to next page
        if(e.getSource() == homePageUI.getJBTNext()){
            if(homePageUI.isValidPage(homePageUI.getPage() + 1)){
                homePageUI.setPage(homePageUI.getPage() + 1);
                homePageUI.displayALLData();//display new page
            }
        }
        
        //move last page
        if(e.getSource() == homePageUI.getJBTLast()){
            if(homePageUI.getURLs().isEmpty())
                homePageUI.setPage(0);
            else
                homePageUI.setPage((homePageUI.getURLs().size() - 1) / homePageUI.getRecordUIs().length);//goto last page
            
            homePageUI.displayALLData();//display new page
        }
        
        //goto addURLPageUI
        if(e.getSource() == homePageUI.getJBTAdd()){
            homePageUI.clearALLData();//clear dirty data homePageUI
            frameUI.setPanel(addURLPageUI.getPanel());//goto addURLPageUI
            frameUI.refrashPanel();
        }
        
        //create HTML file and open in browser
        if(e.getSource() == homePageUI.getJBTExport()){
            if(!(exportHTML() && openHTMLFile()))
                JOptionPane.showMessageDialog(null,
                                            "Unable to open the HTML file in your browser.",
                                            "File Error",
                                            JOptionPane.ERROR_MESSAGE);
        }
        
        //addURLPageUI
        if(e.getSource() == addURLPageUI.getJBTAdd()){
            int choice = JOptionPane.showConfirmDialog(null, "Confirm Add?", "Add Confirmation.", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION)
                addURLBookmark();
        }
        if(e.getSource() == addURLPageUI.getJBTExit()){
            addURLPageUI.clearUserInput();//clear dirty data addURLPageUI
            
            //setup homePageUI data
            homePageUI.setURLs(provider.select());
            homePageUI.displayALLData();
            frameUI.setPanel(homePageUI.getPanel());
            frameUI.refrashPanel();//goto homePageUI
        }
        
        //editURLPageUI
        if(e.getSource() == editURLPageUI.getJBTEdit()){
            if (JOptionPane.showConfirmDialog(null, "Confirm Edit?", "Edit Confirmation.", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
                editURLBookmark();
            editURLPageUI.diaplayAllData();//reset data
        }
        if(e.getSource() == editURLPageUI.getJBTExit()){
            editURLPageUI.clearUserInput();//clear dirty data editURLPageUI
            
            //setup homePageUI data
            homePageUI.setURLs(provider.select());
            homePageUI.displayALLData();
            frameUI.setPanel(homePageUI.getPanel());//goto homePageUI
            frameUI.refrashPanel();
        }
    }//actionPerformed
    
    //for searching button
    private void searchURL(){
        String title = homePageUI.getJTFSearch();
        int categoryIdx = Validation.getCategoryIndex(homePageUI.getJCBCategory());
        
        if((!title.equals("")) && categoryIdx != -1)//both is not empty
            homePageUI.setURLs(provider.selectByTitleRow(title, categoryIdx));
        else if(!title.equals(""))//title is not empty
            homePageUI.setURLs(provider.selectByTitle(title));
        else if(categoryIdx != -1)//category is not empty
            homePageUI.setURLs(provider.selectByRow(categoryIdx));
        else//both is empty
            homePageUI.setURLs(provider.select());
        
        if(homePageUI.getURLs().isEmpty())
            JOptionPane.showMessageDialog(null,
                                    "No URL bookmark was found in the input.",
                                    "Bookmark Not Found",
                                    JOptionPane.INFORMATION_MESSAGE);//no record is found
    }//searchURL
    
    // for addURLPageUI add button
    private void addURLBookmark(){
        URLBookmark newURL = addURLPageUI.getUserInput();
        
        //validate user input
        if(Validation.isTitleLengthOutOfRange(newURL.getTitle())){
            JOptionPane.showMessageDialog(null,
                                        "Title length is out of range. Please enter a valid title (1 - 30).",
                                        "Invalid Title",
                                        JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if(this.provider.findTitleIndex(newURL.getTitle()) != null){
            JOptionPane.showMessageDialog(null,
                                        "This title already exists. Please choose a different one.",
                                        "Invalid Title",
                                        JOptionPane.WARNING_MESSAGE);
            return;
        }

        int categoryIdx = Validation.getCategoryIndex(newURL.getCategory());
        if(categoryIdx == -1){
            JOptionPane.showMessageDialog(null,
                                        "Category cannot be empty.",
                                        "Invalid Category",
                                        JOptionPane.WARNING_MESSAGE);
            return;
        }

        if(!Validation.isValidURL(newURL.getURL())){
            JOptionPane.showMessageDialog(null,
                                        "Invalid URL. Please enter a valid URL starting with http:// or https://",
                                        "Invalid URL",
                                        JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        //after validate insert the data
        provider.insert(categoryIdx, newURL);
        
        if(!provider.writeFile()){
            // If writing to the file fails after insertion, delete the inserted data  to restore original state
            JOptionPane.showMessageDialog(null,
                                    "Failed to add the URL bookmark. Please try again.",
                                    "Added Error",
                                    JOptionPane.ERROR_MESSAGE);
            provider.delete(newURL.getTitle());
        }
        else
            JOptionPane.showMessageDialog(null,
                                    "URL bookmark added successfully.",
                                    "Added Successfully",
                                    JOptionPane.INFORMATION_MESSAGE);

        addURLPageUI.clearUserInput(); 
    }//addURLBookmark
    
    private void editURLBookmark(){          
        URLBookmark newURL = editURLPageUI.getUserInput();
        
        //validate user input
        if(newURL.equals(editURLPageUI.getURL())){
            JOptionPane.showMessageDialog(null,
                                    "No changes were made to the URL.",
                                    "No Changes",
                                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
            
        if(Validation.isTitleLengthOutOfRange(newURL.getTitle())){
            JOptionPane.showMessageDialog(null,
                                        "Title length is out of range. Please enter a valid title (1 - 30).",
                                        "Invalid Title",
                                        JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if(provider.findTitleIndex(newURL.getTitle()) != null  && (!editURLPageUI.getURL().getTitle().equals(newURL.getTitle()))){
            JOptionPane.showMessageDialog(null,
                                        "This title already exists. Please choose a different one.",
                                        "Invalid Title",
                                        JOptionPane.WARNING_MESSAGE);
            return;
        }

        int categoryIdx = Validation.getCategoryIndex(newURL.getCategory());
        if(categoryIdx == -1){
            JOptionPane.showMessageDialog(null,
                                        "Category cannot be empty.",
                                        "Invalid Category",
                                        JOptionPane.WARNING_MESSAGE);
            return;
        }

        if(!Validation.isValidURL(newURL.getURL())){
            JOptionPane.showMessageDialog(null,
                                        "Invalid URL. Please enter a valid URL starting with http:// or https://",
                                        "Invalid URL",
                                        JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        //update URLBookmark
        provider.update(editURLPageUI.getURL().getTitle(), 0, newURL.getTitle());
        provider.update(newURL.getTitle(), 1, newURL.getDescription());
        provider.update(newURL.getTitle(), 2, newURL.getCategory());
        provider.update(newURL.getTitle(), 3, newURL.getURL());
        provider.moveToRow(categoryIdx ,newURL.getTitle());
            
        if(!provider.writeFile()){
            // If writing to the file fails after updating, revert back to the original data
            JOptionPane.showMessageDialog(null,
                                    "Failed to edit URL bookmark. Please try again.",
                                    "Edit Error",
                                    JOptionPane.ERROR_MESSAGE);
            provider.delete(newURL.getTitle());
            provider.update(newURL.getTitle(), 0, editURLPageUI.getURL().getTitle());
            provider.update(editURLPageUI.getURL().getTitle(), 1, editURLPageUI.getURL().getDescription());
            provider.update(editURLPageUI.getURL().getTitle(), 2, editURLPageUI.getURL().getCategory());
            provider.update(editURLPageUI.getURL().getTitle(), 3, editURLPageUI.getURL().getURL());
            int categoryIdxTemp = Validation.getCategoryIndex(editURLPageUI.getURL().getCategory());
            provider.moveToRow(categoryIdxTemp, editURLPageUI.getURL().getTitle());
        }//if
        else{
            JOptionPane.showMessageDialog(null,
                                    "URL bookmark edited successfully.",
                                    "Edited Successfully",
                                    JOptionPane.INFORMATION_MESSAGE);
            editURLPageUI.setURL(newURL);//set to new URL
        }//else

    }//editURLBookmark
    
    //create HTML
    private boolean exportHTML(){
        PrintWriter pw = null;
        ArrayList<URLBookmark> rowRecord = null;
        
        try{
            pw = new PrintWriter(new FileWriter(URLBOOKMARK_HTML_PATH));
            
            pw.printf("<!DOCTYPE html>\n");
            pw.printf("<html>\n");
            pw.printf("<head>\n");
            pw.printf("<meta charset=\"UTF-8\">\n");
            pw.printf("<title>Web URL Bookmark</title>\n");
            pw.printf("</head>\n");
            pw.printf("<body>\n");
            
            pw.printf("<ol type=\"A\">\n");
            
            for(int row = 0; row < Validation.CATEGORY_LIST.length; row++){
                pw.printf("<li>%s\n", escapeHTML(Validation.CATEGORY_LIST[row]));
                pw.printf("<ol type=\"1\">\n");
                
                rowRecord = this.provider.selectByRow(row);
                for(int col = 0; col < rowRecord.size(); col++){
                     pw.printf("<li>%s <a href=\"%s\">%s</a></li>\n",
                            escapeHTML(rowRecord.get(col).getTitle()),
                            escapeHTML(rowRecord.get(col).getURL()),
                            escapeHTML(rowRecord.get(col).getURL()));
                }
                
                pw.printf("</ol>\n");   
                pw.printf("</li>\n");
            }
            
            pw.printf("</ol>\n");
            
            pw.printf("</body>\n");
            pw.printf("</html>\n");

            return true;
        }//try
        catch(IOException e){
            System.out.println("Error: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "");
            return false;
        }//catch
        finally{
            if (pw != null) pw.close();
        }//finally
    }//exportHTML
    
    //open HTML in browser if supported
    private boolean openHTMLFile(){
        try{
            File htmlFile = new File(URLBOOKMARK_HTML_PATH);

            if(Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                desktop.browse(htmlFile.toURI());
                return true;
            }//if
            else{
                JOptionPane.showMessageDialog(null,
                                        "Desktop is not supported on this system.",
                                        "Unsupported System",
                                        JOptionPane.WARNING_MESSAGE);
                return false;
            }//else
        }catch(IOException e){
            System.out.println("Error: " + e.getMessage());
            JOptionPane.showMessageDialog(null,
                                    "Failed to open HTML file.",
                                    "File Opening Error",
                                    JOptionPane.ERROR_MESSAGE);
            return false;
        }//catch
    }//openHTMLFile
    
   /**
    * Escapes special HTML characters in the given input string.
    * Useful for ensuring URLBookmark data is safely displayed in HTML.
    * 
    * @param input the input string to escape
    * @return the escaped HTML string, or null if input is null
    */
    private String escapeHTML(String input){
        if (input == null) return null;
        return input.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "&#39;");
    }//escapeHtml

}//Controller
