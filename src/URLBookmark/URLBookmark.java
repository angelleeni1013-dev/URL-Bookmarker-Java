package URLBookmark;

public class URLBookmark {
    private String title;
    private String description;
    private String category;
    private String url;
    
    //constructor
    public URLBookmark(){
        this.title = "";
        this.description = "";
        this.category = "";
        this.url = "";
    }
    
    public URLBookmark(String title, String description, String category, String url){
        this.title = title;
        this.description = description;
        this.category = category;
        this.url = url;
    }
    
    //copy constructor
    public URLBookmark(URLBookmark obj){
        this.title = obj.getTitle();
        this.description = obj.getDescription();
        this.category = obj.getCategory();
        this.url = obj.getURL();
    }
    
    //setter
    public void setTitle(String title){
        this.title = title;
    }
    
    public void setDescription(String description){
        this.description = description;
    }
        
    public void setCategory(String category){
        this.category = category;
    }
    
    public void setURL(String url){
        this.url = url;
    }
    
    //getter
    public String getTitle(){
        return this.title;
    }
    
    public String getDescription(){
        return this.description;
    }
        
    public String getCategory(){
        return this.category;
    }
    
    public String getURL(){
        return this.url;
    }
    
    /**
     * to check the value of obj is equal with this.value 
     * @param obj the URLBookmark object to need to compare
     * @return if equal return true, else return false
     */
    public boolean equals(URLBookmark obj){
        return this.title.equals(obj.getTitle()) &&
                this.description.equals(obj.getDescription()) &&
                this.category.equals(obj.getCategory()) &&
                this.url.equals(obj.getURL());
    }
    
}//URLBookmark
