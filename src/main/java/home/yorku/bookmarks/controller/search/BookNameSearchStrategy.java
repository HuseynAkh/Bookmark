package home.yorku.bookmarks.controller.search;

import home.yorku.bookmarks.model.BookmarkConstants;
import home.yorku.bookmarks.model.SearchCriteria;

import java.net.MalformedURLException;
import java.net.URL;

//Strategy design pattern: Concrete Strategy
public class BookNameSearchStrategy implements SearchStrategyIF {
    //Giving interface method relevant body
    //Search criteria is in the model, it is used by both the frontend and the backend
    //Getting the search key from searchCriteria then returning the appropriate url associated with the key
    @Override
    public URL getSearchURL(SearchCriteria searchCriteria) {
        System.out.println("BookNameSearchStrategy in action");
        URL url = null;
        if (searchCriteria != null) {
            if (searchCriteria.getSearchKey().equals(BookmarkConstants.KEY_BOOK_NAME)) {
                String search = searchCriteria.getValue().replaceAll(" ", "+");
                try {
                    url = new URL("https://openlibrary.org/search.json?q=" + search);
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return url;
    }
}
