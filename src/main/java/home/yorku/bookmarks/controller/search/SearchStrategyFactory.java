package home.yorku.bookmarks.controller.search;

import home.yorku.bookmarks.model.BookmarkConstants;

//Factory to create SearchStrategy objects
public class SearchStrategyFactory {
    public SearchStrategyIF createSearchStrategy(String key){
        SearchStrategyIF searchStrategy = null;
        if(key.equals(BookmarkConstants.KEY_BOOK_NAME)){
            searchStrategy = new BookNameSearchStrategy();
        }else if(key.equals(BookmarkConstants.KEY_BOOK_AUTHOR)){
            searchStrategy = new BookAuthorSearchStrategy();
        }else if(key.equals(BookmarkConstants.KEY_BOOK_GENRE)){
            searchStrategy = new BookGenreSearchStrategy();
        }else if(key.equals(BookmarkConstants.KEY_MOVIE_TITLE)) {
            searchStrategy = new MovieTitleSearchStrategy();
        }else if(key.equals(BookmarkConstants.KEY_MOVIE_ACTOR)) {
            searchStrategy = new MovieActorSearchStrategy();
        }

        return searchStrategy;
    }


}
