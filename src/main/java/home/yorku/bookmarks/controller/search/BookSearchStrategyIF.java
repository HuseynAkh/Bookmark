package home.yorku.bookmarks.controller.search;

import home.yorku.bookmarks.model.SearchCriteria;

import java.net.URL;

//Strategy design pattern: Strategy Interface
public interface BookSearchStrategyIF {
    public URL getSearchURL(SearchCriteria searchCriteria);
}
