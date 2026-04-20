package URLBookmark;

import java.util.ArrayList;
import java.io.*;
import java.util.*;

public class Provider {
    
    public final static String URLBOOKMARK_TXT_PATH = "URLBookmark.txt";
    private ArrayList<ArrayList<URLBookmark>> urls;
    
    //constructor
    public Provider(){
        this.urls = new ArrayList<>();
        
        for (int i = 0; i < Validation.CATEGORY_LIST.length; i++) {
            this.urls.add(new ArrayList<URLBookmark>());
        }
    }//Provider
    
    /**
     * Select all URLBookmarks.
     * @return a set of URLBookmark copies.
     */
    public ArrayList<URLBookmark> select(){
        ArrayList<URLBookmark> result = new ArrayList<URLBookmark>();
        
        for(int row = 0; row < this.urls.size(); row++){
            for(int col = 0; col < this.urls.get(row).size(); col++){
                result.add(new URLBookmark(this.urls.get(row).get(col)));
            }//for
        }//for
        return result;
    }//select
    
    /**
     * Select URLBookmark where title starts with the provided title.
     * @param title The title to match the beginning of URLBookmark titles.
     * @return A set of URLBookmark copies.
     */
    public ArrayList<URLBookmark> selectByTitle(String title){
        ArrayList<URLBookmark> result = new ArrayList<URLBookmark>();
        
        for(int row = 0; row < this.urls.size(); row++){
            for(int col = 0; col < this.urls.get(row).size(); col++){
                if(this.urls.get(row).get(col).getTitle().toLowerCase().startsWith(title.toLowerCase()))
                    result.add(new URLBookmark(this.urls.get(row).get(col)));
            }//for
        }//for
        return result;
    }//selectByTitle
    
    /**
     * Select URLBookmark by row and return a set of URLBookmark copies.
     * @param row Index of the URL to select
     * @return A set of URLBookmark copies
     */
    public ArrayList<URLBookmark> selectByRow(int row){
        ArrayList<URLBookmark> result = new ArrayList<URLBookmark>();
        
        if(row < 0 || row >= this.urls.size())
            return result;
        
        for(int col = 0; col < this.urls.get(row).size(); col++){
            result.add(new URLBookmark(this.urls.get(row).get(col)));
        }//for
        return result;
    }//selectByRow
    
    /**
    * Selects all URLBookmarks from a specific row where the title starts with the provided title.
    * @param title the title start with (case-insensitive)
    * @param row the row index in the collection
    * @return a list of URLBookmark copies that match the title prefix
    */
    public ArrayList<URLBookmark> selectByTitleRow(String title, int row){
        ArrayList<URLBookmark> result = new ArrayList<URLBookmark>();
        
        if(row < 0 || row >= this.urls.size())
            return result;
        
        for(int col = 0; col < this.urls.get(row).size(); col++){
            if(this.urls.get(row).get(col).getTitle().toLowerCase().startsWith(title.toLowerCase()))
                    result.add(new URLBookmark(this.urls.get(row).get(col)));
        }//for
        return result;
    }//selectByTitleRow
    
    /**
     * Insert copy URLBookmark to this.urls
     * @param row the row need to insert
     * @param url the obj to insert 
     */
    public void insert(int row, URLBookmark url){
        this.urls.get(row).add(new URLBookmark(url));
    }//insert
    
    /**
     * Serach the URLBookmarks need to update by title is provide
     * @param title for searching URLBookmarks
     * @param attribute witch URLBookmarks attribute need to update
     * @param newValue the value to replace
     * @return if not match title will return false, else true
     */
    public boolean update(String title, int attribute, String newValue){
        int[] index = this.findTitleIndex(title);
        
        if(index == null)
            return false;
        
        switch(attribute){
            case 0->{//updtae title
                this.urls.get(index[0]).get(index[1]).setTitle(newValue);
            }//case 0
            case 1->{//updtae description
                this.urls.get(index[0]).get(index[1]).setDescription(newValue);
            }//case 1
            case 2->{//updtae category
                this.urls.get(index[0]).get(index[1]).setCategory(newValue);
            }//case 2
            case 3->{//updtae url
                this.urls.get(index[0]).get(index[1]).setURL(newValue);
            }//case 3
            default->{
                return false;
            }//default
        }//switch 
        return true;
    }//update
    
    /**
     * delete the URLBookmarks based on the title provide
     * @param title the title of URLBookmarks need to delete
     * @return if not match title will return false, else true
     */
    public boolean delete(String title){
        int[] index = this.findTitleIndex(title);
        
        if(index == null)
            return false;
 
        this.urls.get(index[0]).remove(index[1]);
        return true;
    }//delete
    
    /**
     * Get the index x,y is match with title provide
     * @param title the title of URLBookmarks 
     * @return if match return [x,y], else return null
     */
    public int[] findTitleIndex(String title){
        for(int row = 0; row < this.urls.size(); row++){
            for(int col = 0; col < this.urls.get(row).size(); col++){
                if(this.urls.get(row).get(col).getTitle().equals(title))
                    return (new int[]{row, col}); 
            }//for
        }//for
        return null;
    }//findTitleIndex
    
    /**
     * Moves a URLBookmark with the given title to a new row.
     * @param row The destination row to move the bookmark to.
     * @param title The title of the bookmark to move.
     * @return true if the move was successful, false otherwise.
     */
    public boolean moveToRow(int row, String title){
        int[] index = this.findTitleIndex(title);
        
        if(index == null || row < 0 || row >= this.urls.size())
            return false;
        
        URLBookmark tempURL = this.urls.get(index[0]).get(index[1]);
        this.urls.get(index[0]).remove(index[1]);
        this.urls.get(row).add(tempURL);
        return true;
    }
    
    /**
     * Reads data from a file and loads it into the this.urls.
     * @return true if the file was read successfully, false otherwise.
     */
    public boolean readFile(){
        Scanner scFile = null;
        
        final String[] LABLE = {"title", "description", "category", "URL"};
        String[] value = {"","","",""};
        String line = "";
        boolean isFileValid = true;
        
        try{
            scFile = new Scanner(new FileReader(URLBOOKMARK_TXT_PATH));
            
            while(scFile.hasNext()){
                boolean isDataValid = true;
                
                //loop to get valid data
                for(int i = 0; i < LABLE.length; i++){
                    line = scFile.nextLine();
                    if(!line.split(":", 2)[0].equals(LABLE[i])){
                        isDataValid = false;
                        break;
                    }//if
                    value[i] = line.split(":", 2)[1].trim();  
                }//for
                scFile.nextLine();//"\n"
                
                //checking file data
                int row = Validation.getCategoryIndex(value[2]);
                if(isDataValid && 
                    findTitleIndex(value[0]) == null &&
                    !Validation.isTitleLengthOutOfRange(value[0]) &&
                    row != -1 &&
                    Validation.isValidURL(value[3]))
                    this.urls.get(row).add(new URLBookmark(value[0], value[1], value[2], value[3]));
                
                else
                    isFileValid = false;
                
            }//while
            
            return isFileValid;
        }//try
        catch(FileNotFoundException e){
            System.out.println("Error: " + e.getMessage());
            return false;
        }//catch
        catch(NoSuchElementException e){
            System.out.println("Error: " + e.getMessage());
            return false;
        }//catch
        finally{
            if (scFile != null) scFile.close();
        }//finally
    }//readFile
    
    /**
     * Writes the this.urls data to a file.
     * @return true if the file was written successfully, false otherwise.
     */
    public boolean writeFile(){
        PrintWriter pw = null;
        
        try{
            pw = new PrintWriter(new FileWriter(URLBOOKMARK_TXT_PATH));
            
            for(int row = 0; row < this.urls.size(); row++){
                for(int col = 0; col < this.urls.get(row).size(); col++){
                    pw.printf("title: %s\n", this.urls.get(row).get(col).getTitle());
                    pw.printf("description: %s\n", this.urls.get(row).get(col).getDescription());
                    pw.printf("category: %s\n", this.urls.get(row).get(col).getCategory());
                    pw.printf("URL: %s\n", this.urls.get(row).get(col).getURL());
                    pw.printf("\n");
                }//for
            }//for
            return true;
        }//try
        catch(IOException e){
            System.out.println("Error: " + e.getMessage());
            return false;
        }//catch
        finally{
            if (pw != null) pw.close();
        }//finally
    }//writeFile
    
}//Provider
