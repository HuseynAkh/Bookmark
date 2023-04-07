package testing;

import home.yorku.bookmarks.controller.BookmarkController;
import home.yorku.bookmarks.controller.PortfolioController;
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
        SearchCriteria sc = new SearchCriteria(
                BookmarkConstants.TYPE_BOOK,
                BookmarkConstants.KEY_BOOK_NAME,
                "the ghost of graylock");

        BookSearchManager bookSearch = new BookSearchManager();
        PortfolioController pc = new PortfolioController(new BookmarkController());
        Set<Book> BookSet = bookSearch.searchBook(sc);

        for(Book b : BookSet) {
            BookToPortfolio book = new BookToPortfolio(b.getIsbn(), "admin", b.getIdentifier(), b.getTitle(), b.getAuthor(), 0);
            pc.updateBookPortfolio(book, "AddToSavedBooks");



        }






    }
}
