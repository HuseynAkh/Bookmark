package home.yorku.bookmarks.controller.search;

import home.yorku.bookmarks.model.Book;
import home.yorku.bookmarks.model.BookmarkConstants;
import home.yorku.bookmarks.model.Movie;
import home.yorku.bookmarks.model.SearchCriteria;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class MovieSearch {
    SearchStrategyIF searchStrategy = null;

    public Set searchMovie (SearchCriteria searchCriteria){
        Set<Movie> movies = new HashSet<Movie>();
        URL url = null;
        String jsonKey = null;

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

    private static void extractMovieInfo(Set<Movie> movies, StringBuilder informationString, String jsonKey) throws ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(String.valueOf(informationString));
        JSONArray array = new JSONArray();
        array.add(obj);

        JSONObject movieData = (JSONObject) array.get(0);
        JSONArray arr = (JSONArray) movieData.get(jsonKey);
        for (Object o : arr) {
            JSONObject movie = (JSONObject) o;
            String title = (String) movie.get("title"); // get movie title
            String overview = (String) movie.get("overview"); //get movie overview
            String releaseDate = (String) movie.get("release_date"); //get movie release date
            Movie m = new Movie(title, releaseDate, overview);
            movies.add(m);
        }
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
                JSONObject person = (JSONObject) arr.get(0); //grab first actor in result array
                id = (Long) person.get("id"); //retrieve actor id
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }



}

