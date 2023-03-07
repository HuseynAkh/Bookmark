import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Movie {
    private String title;
    private String overview;
    private String releaseDate;

    public Movie(String title, String releaseDate, String overview) {
        this.title = title;
        this.overview = overview;
        this.releaseDate = releaseDate;
    }
    public String getTitle(){
        return this.title;
    }
    public String getOverview(){
        return this.overview;
    }

    public String getReleaseDate(){return this.releaseDate;}

}
