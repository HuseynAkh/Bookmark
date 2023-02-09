package backend;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class MovieSearch {
    private Set<Movie> movieNames = new HashSet<Movie>();
    public   MovieSearch() {
    }
    public Set<Movie> MovieByTitle (String search) {
        movieNames.clear();
        search = search.replaceAll(" ", "+");
        try {
            URL url = new URL("https://api.themoviedb.org/3/search/movie?api_key=9383f37fea2d70dbfae46cb8688e0da3&query=" + search);
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



                JSONObject movieData = (JSONObject) array.get(0);

                JSONArray arr = (JSONArray)movieData.get("results");


                for (Object o : arr) {
                    JSONObject movie = (JSONObject) o;
                    String title = (String) movie.get("title");
                    this.movieNames.add(new Movie(title, ""));
                }
//                for (backend.Movie movie : movieNames) {
//                    System.out.println(movie.getTitle());
//                }

                //System.out.println(bookData.get("id")); // will change according to search variable
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.movieNames;
    }

    public Long GetPersonID(String search){
        search = search.replaceAll(" ", "+");
        Long id = null;
        try {
            URL url = new URL("https://api.themoviedb.org/3/search/person?api_key=9383f37fea2d70dbfae46cb8688e0da3&query=" + search);
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



                JSONObject personData = (JSONObject) array.get(0);

                JSONArray arr = (JSONArray)personData.get("results");

                //Set<String> MovieNames = new HashSet<String>();

                JSONObject person = (JSONObject) arr.get(0);
                id = (Long) person.get("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }

    public Set<Movie> MovieByActor(String name){
        this.movieNames.clear();
        Long id = GetPersonID(name);
        try {
            URL url = new URL("https://api.themoviedb.org/3/person/"+id+"/movie_credits?api_key=9383f37fea2d70dbfae46cb8688e0da3&language=en-US");
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



                JSONObject actorData = (JSONObject) array.get(0);

                JSONArray arr = (JSONArray)actorData.get("cast");

                for (Object o : arr) {
                    JSONObject actor = (JSONObject) o;
                    String title = (String) actor.get("title");
                    this.movieNames.add(new Movie(title, ""));
                }
                //System.out.println(title);

//                for (backend.Movie movie : MovieNames) {
//                    System.out.println(movie.getTitle());
//                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.movieNames;
    }
}
