package URLBookmark;

public class Validation {
    
    public final static int TITLE_SIZE = 30;
    public final static String[] CATEGORY_LIST = {"Music", "Video", "Social Media", "Education", "Gaming"};
    
    /**
    * Checks if the title length exceeds the allowed limit.
    * @param title the title to check
    * @return true if the title is longer than TITLE_SIZE, false otherwise
    */
    public static boolean isTitleLengthOutOfRange(String title){
        return (title.length() > TITLE_SIZE || title.length() < 1);
    }
    
    /**
     * Gets the index of the category from CATEGORY_LIST.
     * @param category the category to search for
     * @return index of category if valid, -1 if not found
     */
    public static int getCategoryIndex(String category){
        for(int i = 0; i < CATEGORY_LIST.length; i++){
            if(CATEGORY_LIST[i].equals(category))
                return i;
        }//for
        return -1;
    }//getRow
    
    /**
     * Checks whether the given URL string is valid.
     * - Must start with "http://" or "https://" (case-insensitive)
     * @param url The URL string to validate
     * @return true if the URL is valid, false otherwise
     */
    public static boolean isValidURL(String url) {
        if (url == null) 
            return false;

        url = url.trim().toLowerCase();
        return url.startsWith("http://") || url.startsWith("https://");
    }
}
