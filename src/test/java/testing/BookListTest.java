package testing;

import home.yorku.bookmarks.controller.BookmarkController;
import home.yorku.bookmarks.controller.PortfolioController;
import home.yorku.bookmarks.controller.search.BookNameSearchStrategy;
import home.yorku.bookmarks.controller.search.BookSearchManager;
import home.yorku.bookmarks.model.*;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Set;

public class BookListTest {

    @Test
    public void bookListTest() throws Exception {
        SearchCriteria sc = new SearchCriteria(
                BookmarkConstants.TYPE_BOOK,
                BookmarkConstants.KEY_BOOK_NAME,
                "the ghost of graylock");

        BookSearchManager bookSearch = new BookSearchManager();
        PortfolioController pc = new PortfolioController(new BookmarkController());
        Method method = PortfolioController.class.getDeclaredMethod("updateBookPortfolio", BookToPortfolio.class, String.class);
        method.setAccessible(true);
        //method.invoke(pc,book, "AddToSavedBooks");

    }


}
