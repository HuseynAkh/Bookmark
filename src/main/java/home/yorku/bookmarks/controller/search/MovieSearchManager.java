package home.yorku.bookmarks.controller.search;

import home.yorku.bookmarks.model.BookmarkConstants;
import home.yorku.bookmarks.model.Movie;
import home.yorku.bookmarks.model.SearchCriteria;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

//Strategy design pattern: Context and Client implementation
//This class behaves as the manager for the movie search
//It creates a new strategy object of type SearchStategyIF
//It is getting the key from the SearchCriteria from the model and instantiates the relevant search strategy needed
public class MovieSearchManager {
    SearchStrategyIF searchStrategy = null;

    public Set searchMovie (SearchCriteria searchCriteria){
        Set<Movie> movies = new HashSet<Movie>();
        URL url = null;
        String jsonKey = null;
        //getting the key from searchCriteria
        //instantiating the relevant search strategy based on the key
        if(searchCriteria.getSearchKey().equals(BookmarkConstants.KEY_MOVIE_TITLE)){
            searchStrategy = new MovieTitleSearchStrategy();
            jsonKey = BookmarkConstants.JSON_KEY_MOVIE_RESULTS;
        }else if(searchCriteria.getSearchKey().equals(BookmarkConstants.KEY_MOVIE_ACTOR)){

            //call on GetPersonID method to get ID of the actor searched
            System.out.println("actor name = "+searchCriteria.getValue());
            Long id = GetPersonID(searchCriteria.getValue());
            System.out.println("actor ID obtained = "+id);

            //set above ID in the search criteria
            searchCriteria = new SearchCriteria(searchCriteria.getType(), searchCriteria.getSearchKey(), id.toString());
            searchStrategy = new MovieActorSearchStrategy();
            url = searchStrategy.getSearchURL(searchCriteria);
            jsonKey = BookmarkConstants.JSON_KEY_MOVIE_CAST;

        }

        searchMoviesAPI(movies, searchStrategy.getSearchURL(searchCriteria), jsonKey );
        return movies;
    }

    //This method gets the json from the url
    private static void searchMoviesAPI(Set<Movie> movies, URL url, String jsonKey) {
        try {
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

                extractMovieInfo(movies, informationString, jsonKey);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //this method gets the information of the movies and adds it to the movies hashset
    private static void extractMovieInfo(Set<Movie> movies, StringBuilder informationString, String jsonKey) throws ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(String.valueOf(informationString));
        JSONArray array = new JSONArray();
        array.add(obj);

        JSONObject movieData = (JSONObject) array.get(0);
        JSONArray arr = (JSONArray) movieData.get(jsonKey);
        for (Object o : arr) {
            JSONObject movie = (JSONObject) o;
            // get movie title
            String title = (String) movie.get("title");
            //get movie overview
            String overview = (String) movie.get("overview");
            //get movie release date
            String releaseDate = (String) movie.get("release_date");
            Movie m = new Movie(title, releaseDate, overview);
            movies.add(m);
        }
    }

    //helper method that gets the actor id, which is needed for the url of MovieActorSearchStrategy
    public Long GetPersonID(String search){
        //get the ID of the actor being searched. This id is used to get list of movies actor has acted in.
        search = search.replaceAll(" ", "+"); //format fo url
        Long id = null;
        try {
            //this url is specific to search for actor
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
                    //grab JSON text result
                    informationString.append((scanner.nextLine()));
                }
                scanner.close();

                JSONParser parser = new JSONParser();
                Object obj = parser.parse(String.valueOf(informationString)); //parse JSON Text result to String
                JSONArray array = new JSONArray();
                array.add(obj);

                JSONObject personData = (JSONObject) array.get(0);
                JSONArray arr = (JSONArray)personData.get("results"); //get array in JSON text result tagged "results"
                JSONObject person = (JSONObject) arr.get(0); //grab first actor in result array
                id = (Long) person.get("id"); //retrieve actor id
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }



}

