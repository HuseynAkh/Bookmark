package home.yorku.bookmarks.controller.search;

import home.yorku.bookmarks.model.SearchCriteria;

import java.net.MalformedURLException;
import java.net.URL;

//Strategy design pattern: Concrete Strategy
public class BookGenreSearchStrategy implements SearchStrategyIF {
    //Giving interface method relevant body
    //Search criteria is in the model, it is used by both the frontend and the backend
    //Getting the search key from searchCriteria then returning the appropriate url associated with the key
    @Override
    public URL getSearchURL(SearchCriteria searchCriteria) {
        System.out.println("BookGenreSearchStrategy in action");
        URL url = null;
        String search = searchCriteria.getValue().replaceAll(" ", "%20");
        try {
            url = new URL("https://openlibrary.org/search.json?subject=" + search + "&sort=new");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        return url;
    }
}
