package home.yorku.bookmarks.controller.search;

import home.yorku.bookmarks.model.SearchCriteria;

import java.net.MalformedURLException;
import java.net.URL;

//Strategy design pattern: Concrete Strategy
public class BookAuthorSearchStrategy implements SearchStrategyIF {
    //Giving interface method relevant body
    @Override
    public URL getSearchURL(SearchCriteria searchCriteria) {
        System.out.println("BookAuthorSearchStrategy in action");
        String search = searchCriteria.getValue().replaceAll(" ", "%20");
        URL url = null;
        try {
            url = new URL("https://openlibrary.org/search.json?author=" + search + "&sort=new");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        return url;
    }
}
