package home.yorku.bookmarks.controller.search;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class JsonInfoParser {
    private Scanner scanner; //Scanner object for URL search
    private JSONParser parser; //JSONParser Object from library to parse information
    private Object obj; //Object Object to store JSON Object
    private JSONArray array; //JSON array for the same reasons as state previously
    private JSONObject jsonData;

    public JsonInfoParser(){
        this.parser = new JSONParser();
        this.array = new JSONArray();
        this.jsonData = null;
    }



    public JSONObject getJsonInfo(String urlString) { // search url and retrieve JSON text information

        try {
            URL url = new URL(urlString); //convert String url passed from caller to URL Object
            HttpURLConnection con = (HttpURLConnection) url.openConnection(); //open HTTP Connection and search url
            con.setRequestMethod("GET");
            con.connect();
            int responseCode = con.getResponseCode();

            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {
                StringBuilder informationString = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    informationString.append((scanner.nextLine())); //grab JSON text result
                }
                scanner.close();

                JSONParser parser = new JSONParser();
                Object obj = parser.parse(String.valueOf(informationString)); //pase JSON text result into String result
                JSONArray array = new JSONArray();
                array.add(obj);
                JSONObject data = (JSONObject) array.get(0);

                return data; //return single JSON object with the entire JSON text result.

            }
        } catch (Exception e) { //this exception catches the case where the user enters a book or movie that does not exist. it will do nothing.
            e.printStackTrace();
            return null;
        }
    }

}
