package testing;

import home.yorku.bookmarks.controller.search.BookSearchManager;
import home.yorku.bookmarks.controller.search.MovieTitleSearchStrategy;
import home.yorku.bookmarks.model.Book;
import home.yorku.bookmarks.model.BookmarkConstants;
import home.yorku.bookmarks.model.SearchCriteria;
import org.junit.jupiter.api.Assertions;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Set;

public class BookAuthorTest {

    @Test
    void bookAuthorTest() throws MalformedURLException {

        MovieTitleSearchStrategy urlTester = new MovieTitleSearchStrategy();
        SearchCriteria searchCriteria = new SearchCriteria(
                BookmarkConstants.TYPE_BOOK,
                BookmarkConstants.KEY_BOOK_AUTHOR,
                "the ghost of graylock");

        BookSearchManager bm = new BookSearchManager();
        Set<Book> bookSet = bm.searchBook(searchCriteria);

        ArrayList<String> author = bookSet.iterator().next().getAuthor();

        Assertions.assertEquals("Dan Poblocki", author.get(0));

    }
}
