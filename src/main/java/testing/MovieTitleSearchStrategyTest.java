package testing;

import home.yorku.bookmarks.controller.search.MovieTitleSearchStrategy;
import home.yorku.bookmarks.model.SearchCriteria;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

class MovieTitleSearchStrategyTest {

    //This test tests to ensure the URL for searching by movie name is correct
    @Test
    void getSearchURLRegTitleSearch() throws MalformedURLException {

        MovieTitleSearchStrategy urlTester = new MovieTitleSearchStrategy();
        String type = "movie";
        String searchKey = "title";
        String value = "cars";
        URL expected = new URL("https://api.themoviedb.org/3/search/movie?api_key=9383f37fea2d70dbfae46cb8688e0da3&query=cars");
        SearchCriteria searchCriteria = new SearchCriteria(type,searchKey,value);

        URL actual = urlTester.getSearchURL(searchCriteria);

        assertEquals(expected, actual);
    }
    //This test tests to ensure the URL for when a search is null is correct
    @Test
    void getSearchURLNullTitleSearch() throws MalformedURLException {

        MovieTitleSearchStrategy urlTester = new MovieTitleSearchStrategy();
        String type = "movie";
        String searchKey = "title";
        String value = "";
        URL expected = new URL("https://api.themoviedb.org/3/search/movie?api_key=9383f37fea2d70dbfae46cb8688e0da3&query=");
        SearchCriteria searchCriteria = new SearchCriteria(type,searchKey,value);

        URL actual = urlTester.getSearchURL(searchCriteria);

        assertEquals(expected, actual);
    }
    //This test tests to ensure the URL for when a search is incorrect is correct
    @Test
    void getSearchURLIncorrectTtileSearch() throws MalformedURLException {

        MovieTitleSearchStrategy urlTester = new MovieTitleSearchStrategy();
        String type = "movie";
        String searchKey = "title";
        String value = "Dwayne Johnson";
        URL expected = new URL("https://api.themoviedb.org/3/search/movie?api_key=9383f37fea2d70dbfae46cb8688e0da3&query=Dwanye+Johnson");
        SearchCriteria searchCriteria = new SearchCriteria(type,searchKey,value);

        URL actual = urlTester.getSearchURL(searchCriteria);

        assertEquals(expected, actual);
    }
}