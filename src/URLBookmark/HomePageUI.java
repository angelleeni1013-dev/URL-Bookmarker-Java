package URLBookmark;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class HomePageUI {
    private ArrayList<URLBookmark> urls;
    private int page;
    
    private JPanel panel;
    private JTextField jtfSearch;
    private JComboBox jcbCategory;
    private JButton jbtSearch, jbtFirst, jbtPrevious, jbtNext, jbtLast, jbtAdd, jbtExport;
    
    private RecordUI[] recordUIs = {new RecordUI(), new RecordUI(), new RecordUI(), new RecordUI(), new RecordUI()};
    
    
    public HomePageUI(){
        urls = new ArrayList<URLBookmark>();
        page = 0;
        
        panel = new JPanel();
        panel.setLayout(null);
        
//        title
        JLabel jlbPageTitle = new JLabel("URL Bookmark Software");
        jlbPageTitle.setFont(new Font("Arial", Font.BOLD, 30));
        jlbPageTitle.setBounds(40,30,400,30);  
        panel.add(jlbPageTitle);
        
        //line
        JSeparator jsLine = new JSeparator(SwingConstants.HORIZONTAL);
        jsLine.setForeground(Color.BLACK);
        jsLine.setBounds(30,80,720,15);
        panel.add(jsLine);

        //search
        jtfSearch = new JTextField(30);
        jtfSearch.setFont(new Font("Arial", Font.PLAIN, 16));
        jtfSearch.setBounds(70,110,400,25);
        jcbCategory = new JComboBox();
        jcbCategory.setFont(new Font("Arial", Font.PLAIN, 16));
        jcbCategory.setBounds(480,110,100,25);
        jcbCategory.setBackground(Color.WHITE);
        jcbCategory.addItem("");
        for (String category : Validation.CATEGORY_LIST) {
            jcbCategory.addItem(category);
        }//for
        jbtSearch = new JButton("Search");
        jbtSearch.setFont(new Font("Arial", Font.PLAIN, 16));
        jbtSearch.setBounds(590,110,100,25);
        panel.add(jtfSearch);
        panel.add(jcbCategory);
        panel.add(jbtSearch);
        
        //record
        for(int i = 0; i < recordUIs.length; i++){
            recordUIs[i].getPanel().setBounds(70,i*50+180,620,50);
            panel.add(recordUIs[i].getPanel());
        }
        
        //
        jbtFirst = new JButton("<<");
        jbtFirst.setBounds(70,460,70,25);
        jbtFirst.setFont(new Font("Arial", Font.PLAIN, 16));
        jbtPrevious = new JButton("<");
        jbtPrevious.setBounds(150,460,70,25);
        jbtPrevious.setFont(new Font("Arial", Font.PLAIN, 16));
        jbtNext = new JButton(">");
        jbtNext.setBounds(230,460,70,25);
        jbtNext.setFont(new Font("Arial", Font.PLAIN, 16));
        jbtLast = new JButton(">>");
        jbtLast.setBounds(310,460,70,25);
        jbtLast.setFont(new Font("Arial", Font.PLAIN, 16));
        
        panel.add(jbtFirst);
        panel.add(jbtNext);
        panel.add(jbtPrevious);
        panel.add(jbtLast);
        
        jbtAdd = new JButton("Add");
        jbtAdd.setBounds(480,460,100,25);
        jbtAdd.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(jbtAdd);
        
        jbtExport = new JButton("Export");
        jbtExport.setBounds(590,460,100,25);
        jbtExport.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(jbtExport);
    }
    
    public void setURLs(ArrayList<URLBookmark> urls){
        this.urls = urls;
    }
    
    public ArrayList<URLBookmark> getURLs(){
        return this.urls;
    }
    
    public void setPage(int page){
        this.page = page;
    }
    
    public int getPage(){
        return this.page;
    }
    
    public JPanel getPanel(){
        return this.panel;
    }
    
    public String getJTFSearch(){
        return this.jtfSearch.getText().trim();
    }
    
    public String getJCBCategory(){
        return (String)this.jcbCategory.getSelectedItem();
    }
    
    public JButton getJBTSearch(){
        return this.jbtSearch;
    }
    
    public JButton getJBTFirst(){
        return this.jbtFirst;
    }
    
    public JButton getJBTNext(){
        return this.jbtNext;
    }
    
    public JButton getJBTPrevious(){
        return this.jbtPrevious;
    }
    
    public JButton getJBTLast(){
        return this.jbtLast;
    }
    
    public JButton getJBTAdd(){
        return this.jbtAdd;
    }
    
    public JButton getJBTExport(){
        return this.jbtExport;
    }
    
    public RecordUI[] getRecordUIs(){
        return this.recordUIs;
    }
    
    public void displayALLData(){
        for(int i = 0; i < recordUIs.length; i++){
            if((page * recordUIs.length + i) < urls.size()){
                recordUIs[i].setURL(urls.get(page * recordUIs.length + i));
                recordUIs[i].displayALLData();
                recordUIs[i].getPanel().setVisible(true);
            }//if
            else
                recordUIs[i].getPanel().setVisible(false);
        }//for
    }//setRecord
    
    public void clearALLData(){
        this.urls = new ArrayList<URLBookmark>();
        this.page = 0;
        this.jtfSearch.setText("");
        this.jcbCategory.setSelectedItem("");
        
        for(int i = 0; i < recordUIs.length; i++){
            recordUIs[i].clearALLData();
            recordUIs[i].getPanel().setVisible(false);
        }
    }//claerALLData
    
    public boolean isValidPage(int numPage){
        if(numPage < 0)
            return false;
        
        if(urls.isEmpty())
            return (numPage == 0);

        return (numPage <= (urls.size() - 1) / recordUIs.length);
    }//isValidPage
    
}//HomePageUI
