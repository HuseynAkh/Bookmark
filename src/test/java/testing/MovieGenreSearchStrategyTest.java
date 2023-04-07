package testing;

import home.yorku.bookmarks.controller.search.BookSearchManager;
import home.yorku.bookmarks.controller.search.MovieSearchManager;
import home.yorku.bookmarks.model.BookmarkConstants;
import home.yorku.bookmarks.model.Movie;
import home.yorku.bookmarks.model.SearchCriteria;
import home.yorku.bookmarks.controller.search.MovieGenreSearchStrategy;
import org.junit.jupiter.api.Assertions;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

public class MovieGenreSearchStrategyTest {


    @Test
    void getSearchURLRegGenreSearch() throws MalformedURLException{
        MovieGenreSearchStrategy genreURL = new MovieGenreSearchStrategy();
        SearchCriteria sc = new SearchCriteria(BookmarkConstants.TYPE_MOVIE, BookmarkConstants.KEY_MOVIE_GENRE, "28");

        URL expectedURL = new URL("https://api.themoviedb.org/3/discover/movie?api_key=xxx&with_genres=28");
        URL actualURL = genreURL.getSearchURL(sc);

        MovieSearchManager movieSearch = new MovieSearchManager();
        Set<Movie> result = movieSearch.searchMovie(sc);

        Assertions.assertEquals(expectedURL, actualURL);
        Assertions.assertNotNull(result);

    }

}
