package testing;

import home.yorku.bookmarks.controller.search.MovieSearchManager;
import home.yorku.bookmarks.controller.search.MovieTitleSearchStrategy;
import home.yorku.bookmarks.model.Movie;
import home.yorku.bookmarks.model.SearchCriteria;
import org.testng.annotations.Test;
import java.util.Iterator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MovieSearchManagerTest {

    @Test
    void getMovieOverview() {
        MovieSearchManager mvs = new MovieSearchManager();

        String type = "movie";
        String searchKey = "title";
        String value = "cars";
        SearchCriteria searchCriteria = new SearchCriteria(type,searchKey,value);
        Set<Movie> expectedSet = mvs.searchMovie(searchCriteria);

        Iterator<Movie> itr = expectedSet.iterator();

        String actualDescription = itr.next().getOverview();
        assertNotNull(actualDescription);
        while(itr.hasNext()){
            if(itr.next().getTitle().equals("Cars 3")){
                //assertEquals(description);
            }
        }
        }
        String expectedOverview = "Blindsided by a new generation of blazing-fast racers, the legendary Lightning McQueen is suddenly pushed out of the sport he loves. To get back in the game, he will need the help of an eager young race technician with her own plan to win, inspiration from the late Fabulous Hudson Hornet, and a few unexpected turns. Proving that #95 isn't through yet will test the heart of a champion on Piston Cup Racing’s biggest stage!";
    }
}