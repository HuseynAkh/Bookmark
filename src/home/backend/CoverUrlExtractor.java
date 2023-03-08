package home.backend;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class CoverUrlExtractor {
    private JsonInfoParser jip;
    private ImageDownloader imgDL;
    public CoverUrlExtractor(){
        this.jip = new JsonInfoParser();
        this.imgDL = new ImageDownloader();

    }



    public void getBookCover(String isbn) {
        JSONObject isbnData = this.jip.getJsonInfo("https://openlibrary.org/api/books?bibkeys=OLID:" + isbn + "&jscmd=data&format=json");
        isbnData = (JSONObject) isbnData.get("ISBN:"+isbn);
        try {
            isbnData = (JSONObject) isbnData.get("cover");
            System.out.println(isbnData);
            String coverURL = (String) isbnData.get("large");
            this.imgDL.downloadImage(coverURL, isbn);
        }catch (NullPointerException e){

        }

    }
}
