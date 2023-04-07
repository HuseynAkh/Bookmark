package testing;

import home.yorku.bookmarks.controller.search.MovieSearchManager;
import home.yorku.bookmarks.controller.search.MovieTitleSearchStrategy;
import home.yorku.bookmarks.model.Movie;
import home.yorku.bookmarks.model.SearchCriteria;
import org.junit.jupiter.api.Assertions;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

public class MovieSynopsisTest {
    @Test
    void MovieOverviewTest() throws MalformedURLException {

        MovieTitleSearchStrategy urlTester = new MovieTitleSearchStrategy();
        String type = "KEY_MOVIE";
        String searchKey = "KEY_MOVIE_TITLE";
        String value = "cars";
        SearchCriteria sc = new SearchCriteria(type,searchKey,value);

        MovieSearchManager movieSearch = new MovieSearchManager();
        Set<Movie> result = movieSearch.searchMovie(sc);

        Movie testMovie = result.iterator().next();

        Assertions.assertNotNull(testMovie.getOverview());

    }
}
