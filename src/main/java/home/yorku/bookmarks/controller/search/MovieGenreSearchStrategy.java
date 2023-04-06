package home.yorku.bookmarks.controller.search;

import home.yorku.bookmarks.model.BookmarkConstants;
import home.yorku.bookmarks.model.SearchCriteria;

import java.net.MalformedURLException;
import java.net.URL;

public class MovieGenreSearchStrategy implements SearchStrategyIF{

    //SearchCriteria search = new SearchCriteria(BookmarkConstants.KEY_MOVIE_GENRE, BookmarkConstants.KEY_MOVIE_TITLE);

    @Override
    public URL getSearchURL(SearchCriteria searchCriteria) {

        URL url = null;
        if (searchCriteria != null) {
            if (searchCriteria.getSearchKey().equals(BookmarkConstants.KEY_MOVIE_GENRE)) {
                try {
                    url = new URL("https://api.themoviedb.org/3/discover/movie?api_key=9383f37fea2d70dbfae46cb8688e0da3&with_genres="+searchCriteria.getValue());
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return url;
    }

}
