package home.yorku.bookmarks.controller.search;

import org.json.simple.JSONObject;

public class CoverUrlExtractor {
    private JsonInfoParser jip; //JsonInfoParser object to parse JSON text into String
    private ImageDownloader imgDL; //ImageDownloader object to download and store book cover images

    public String coverURL; //FOR TESTING PURPOSES ONLY. THIS WILL BE PRIVATE IN FINISHED PRODUCT
    public CoverUrlExtractor(){ //contructor
        this.jip = new JsonInfoParser();
        this.imgDL = new ImageDownloader();

    }

    public boolean getBookCover(String isbn) { // get the book cover image. (This method is not exactly SRP, we must create a new class BookCover which calls upon this class.
        JSONObject isbnData = this.jip.getJsonInfo("https://openlibrary.org/api/books?bibkeys=OLID:" + isbn + "&jscmd=data&format=json"); //call on JsonInfoParser to search url and return JSON result
        isbnData = (JSONObject) isbnData.get("OLID:"+isbn); //convert entire json text into json object
        try {
            isbnData = (JSONObject) isbnData.get("cover"); //grab the array in JSON text for book cover image urls
           this.coverURL = (String) isbnData.get("large"); //grab url for largest book cover jpg image
            this.imgDL.downloadImage(this.coverURL, isbn); //call upon ImageDownloader to download and store image
            return true;
        }catch (NullPointerException e){ //some books do not have covers. this exception catch will catch NPE to reduce software lag and to assign a placeholder image as book cover
            return false;
        }

    }

}


