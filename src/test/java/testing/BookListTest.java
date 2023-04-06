package testing;

import home.yorku.bookmarks.controller.search.BookNameSearchStrategy;
import home.yorku.bookmarks.controller.search.BookSearchManager;
import home.yorku.bookmarks.model.*;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.net.MalformedURLException;
import java.util.Set;

public class BookListTest {

    @Test
    public void bookListTest() throws MalformedURLException{
        BookSearchManager bm = new BookSearchManager();
        Set<Book> bookResults = bm.searchBook(new SearchCriteria(BookmarkConstants.TYPE_BOOK, BookmarkConstants.KEY_BOOK_NAME, "the ghost of graylock"));
        Assertions.assertEquals(5, bookResults.size());




    }
}
