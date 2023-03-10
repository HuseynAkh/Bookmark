package testing;

import home.yorku.bookmarks.controller.search.MovieTitleSearchStrategy;
import home.yorku.bookmarks.model.SearchCriteria;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;


class MovieActorSearchStrategyTest {

    //This test tests to ensure the URL for searching by actor name is correct
    @Test
    void getSearchURLRegActorSearch() throws MalformedURLException {

        MovieTitleSearchStrategy urlTester = new MovieTitleSearchStrategy();
        String type = "movie";
        String searchKey = "actor";
        String value = "Mel Gibson";
        URL expected = new URL("https://api.themoviedb.org/3/person/Mel+Gibson/movie_credits?api_key=9383f37fea2d70dbfae46cb8688e0da3&language=en-US");
        SearchCriteria searchCriteria = new SearchCriteria(type,searchKey,value);

        URL actual = urlTester.getSearchURL(searchCriteria);

        assertEquals(expected, actual);
    }
    //This test tests to ensure the URL for when a search is null is correct
    void getSearchURLNullActorSearch() throws MalformedURLException {

        MovieTitleSearchStrategy urlTester = new MovieTitleSearchStrategy();
        String type = "movie";
        String searchKey = "actor";
        String value = "";
        URL expected = new URL("https://api.themoviedb.org/3/person//movie_credits?api_key=9383f37fea2d70dbfae46cb8688e0da3&language=en-US");
        SearchCriteria searchCriteria = new SearchCriteria(type,searchKey,value);

        URL actual = urlTester.getSearchURL(searchCriteria);

        assertEquals(expected, actual);
    }
    //This test tests to ensure the URL for when a search is incorrect is correct
    void getSearchURLIncorrectActorSearch() throws MalformedURLException {

        MovieTitleSearchStrategy urlTester = new MovieTitleSearchStrategy();
        String type = "movie";
        String searchKey = "actor";
        String value = "Cars";
        URL expected = new URL("https://api.themoviedb.org/3/person/Cars/movie_credits?api_key=9383f37fea2d70dbfae46cb8688e0da3&language=en-US");
        SearchCriteria searchCriteria = new SearchCriteria(type,searchKey,value);

        URL actual = urlTester.getSearchURL(searchCriteria);

        assertEquals(expected, actual);
    }
}