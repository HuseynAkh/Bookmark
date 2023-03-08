package home.yorku.bookmarks.controller.search;

import home.yorku.bookmarks.model.SearchCriteria;

import java.net.MalformedURLException;
import java.net.URL;

//Strategy design pattern: Concrete Strategy
public class BookGenreSearchStrategy implements BookSearchStrategyIF{
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
