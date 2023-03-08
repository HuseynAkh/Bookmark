package home.Search;

import home.backend.Movie;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class MovieSearch {
    private Set<Movie> movieNames = new HashSet<Movie>(); //set to store movie results
    public   MovieSearch() {
    }
    public Set<Movie> MovieByTitle (String search) { //search movie by movie title
        movieNames.clear(); // clear existing movie result list
        search = search.replaceAll(" ", "+"); //format for url
        try {
            URL url = new URL("https://api.themoviedb.org/3/search/movie?api_key=9383f37fea2d70dbfae46cb8688e0da3&query=" + search); //this URL is specific to search movie by title
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
                    informationString.append((scanner.nextLine()));//grab JSON text information
                }
                scanner.close();


                JSONParser parser = new JSONParser();
                Object obj = parser.parse(String.valueOf(informationString)); //parse JSON text into String
                JSONArray array = new JSONArray();
                array.add(obj);



                JSONObject movieData = (JSONObject) array.get(0);

                JSONArray arr = (JSONArray)movieData.get("results"); //grab JSON array tagged "results" in JSON Text result


                for (Object o : arr) { //for each movie result in JSON array
                    JSONObject movie = (JSONObject) o;
                    String title = (String) movie.get("title"); // get movie title
                    String overview = (String) movie.get("overview"); //get movie overview
                    String releaseDate = (String) movie.get("release_date"); //get movie release date

                    this.movieNames.add(new Movie(title,releaseDate, overview)); //create new movie object with retrieved information and add to list
                }
//                for (backend.Movie movie : movieNames) {
//                    System.out.println(movie.getTitle());
//                    }

                //System.out.println(bookData.get("id")); // will change according to search variable
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.movieNames;
    }

    public Long GetPersonID(String search){ //get the ID of the actor being searched. This id is used to get list of movies actor has acted in.
        search = search.replaceAll(" ", "+"); //format fo url
        Long id = null;
        try {
            URL url = new URL("https://api.themoviedb.org/3/search/person?api_key=9383f37fea2d70dbfae46cb8688e0da3&query=" + search); //this url is specific to search for actor
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
                    informationString.append((scanner.nextLine())); //grab JSON text result
                }
                scanner.close();


                JSONParser parser = new JSONParser();
                Object obj = parser.parse(String.valueOf(informationString)); //parse JSON Text result to String
                JSONArray array = new JSONArray();
                array.add(obj);



                JSONObject personData = (JSONObject) array.get(0);

                JSONArray arr = (JSONArray)personData.get("results"); //get array in JSON text result tagged "results"

                //Set<String> MovieNames = new HashSet<String>();

                JSONObject person = (JSONObject) arr.get(0); //grab first actor in result array
                id = (Long) person.get("id"); //retrieve actor id
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }

    public Set<Movie> MovieByActor(String name){ //search movies that a specific actor acted in
        this.movieNames.clear(); //clear previous result list
        Long id = GetPersonID(name); //call on GetPersonID method to get ID of the actor searched
        try {
            URL url = new URL("https://api.themoviedb.org/3/person/"+id+"/movie_credits?api_key=9383f37fea2d70dbfae46cb8688e0da3&language=en-US"); //this url is specific to search movies that a specific actor acted in
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
                    informationString.append((scanner.nextLine())); //grab JSON text result
                }
                scanner.close();


                JSONParser parser = new JSONParser();
                Object obj = parser.parse(String.valueOf(informationString)); //parse JSON text result into String
                JSONArray array = new JSONArray();
                array.add(obj);



                JSONObject actorData = (JSONObject) array.get(0);

                JSONArray arr = (JSONArray)actorData.get("cast"); // grab JSON section for movies that actor casted in

                for (Object o : arr) { //for each movie that the actor casted in
                    JSONObject actor = (JSONObject) o;
                    String title = (String) actor.get("title"); //get movie title
                    String overview = (String) actor.get("overview"); //get movie overview
                    String releaseDate = (String) actor.get("release_date"); //get movie release date

                    this.movieNames.add(new Movie(title,releaseDate, overview)); //create new movie object with information retrieved and store in result list.
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
