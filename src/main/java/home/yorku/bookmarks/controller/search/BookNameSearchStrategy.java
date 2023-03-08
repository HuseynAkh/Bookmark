package home.yorku.bookmarks.controller.search;

import home.yorku.bookmarks.model.Book;
import home.yorku.bookmarks.model.BookmarkConstants;
import home.yorku.bookmarks.model.SearchCriteria;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

//Strategy design pattern: Concrete Strategy
public class BookNameSearchStrategy implements BookSearchStrategyIF {

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
