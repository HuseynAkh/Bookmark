package home.backend;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class JsonInfoParser {
    private Scanner scanner;
    private JSONParser parser;
    private Object obj;
    private JSONArray array;
    private JSONObject jsonData;

    public JsonInfoParser(){
        this.parser = new JSONParser();
        this.array = new JSONArray();
        this.jsonData = null;
    }



    public JSONObject getJsonInfo(String urlString) {

        try {
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int responseCode = con.getResponseCode();

            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {
                StringBuilder informationString = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    informationString.append((scanner.nextLine()));
                }
                scanner.close();

                JSONParser parser = new JSONParser();
                Object obj = parser.parse(String.valueOf(informationString));
                JSONArray array = new JSONArray();
                array.add(obj);
                JSONObject data = (JSONObject) array.get(0);

                return data;

            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
