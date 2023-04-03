package testing;

import home.yorku.bookmarks.controller.search.MovieActorSearchStrategy;
import home.yorku.bookmarks.controller.search.MovieSearchManager;
import home.yorku.bookmarks.controller.search.MovieTitleSearchStrategy;
import home.yorku.bookmarks.model.SearchCriteria;
import org.junit.jupiter.api.Assertions;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;


public class MovieActorSearchStrategyTest {

    //This test tests to ensure the URL for searching by actor name is correct
    @Test
    void getSearchURLRegActorSearch() throws MalformedURLException {

        MovieActorSearchStrategy urlTester = new MovieActorSearchStrategy();
        String type = "TYPE_MOVIE";
        String searchKey = "KEY_MOVIE_ACTOR";
        String value = "Mel Gibson";

        URL expected = new URL("https://api.themoviedb.org/3/person/Mel Gibson/movie_credits?api_key=9383f37fea2d70dbfae46cb8688e0da3&language=en-US");
        SearchCriteria searchCriteria = new SearchCriteria(type,searchKey,value);
        URL actual = urlTester.getSearchURL(searchCriteria);

        Assertions.assertEquals(expected, actual);
    }
    //This test tests to ensure the URL for when a search is null is correct
    @Test
    void getSearchURLNullActorSearch() throws MalformedURLException {

        MovieActorSearchStrategy urlTester = new MovieActorSearchStrategy();
        String type = "TYPE_MOVIE";
        String searchKey = "KEY_MOVIE_ACTOR";
        String value = "";

        URL expected = new URL("https://api.themoviedb.org/3/person//movie_credits?api_key=9383f37fea2d70dbfae46cb8688e0da3&language=en-US");
        SearchCriteria searchCriteria = new SearchCriteria(type,searchKey,value);

        URL actual = urlTester.getSearchURL(searchCriteria);

        Assertions.assertEquals(expected, actual);
    }
    //This test tests to ensure the URL for when a search is incorrect is correct
    @Test
    void getSearchURLIncorrectActorSearch() throws MalformedURLException {

        MovieActorSearchStrategy urlTester = new MovieActorSearchStrategy();
        String type = "TYPE_MOVIE";
        String searchKey = "KEY_MOVIE_ACTOR";
        String value = "Cars";
        URL expected = new URL("https://api.themoviedb.org/3/person/Cars/movie_credits?api_key=9383f37fea2d70dbfae46cb8688e0da3&language=en-US");
        SearchCriteria searchCriteria = new SearchCriteria(type,searchKey,value);

        URL actual = urlTester.getSearchURL(searchCriteria);

        Assertions.assertEquals(expected, actual);
    }
}