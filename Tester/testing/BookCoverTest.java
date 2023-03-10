package testing;

import home.yorku.bookmarks.controller.search.CoverUrlExtractor;
import org.junit.jupiter.api.Assertions;
import org.testng.annotations.Test;

import java.net.MalformedURLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookCoverTest {

    @Test
    void getSearchJSONBookCover() throws MalformedURLException {
        String OLID = "OL22856696M";

        CoverUrlExtractor cue = new CoverUrlExtractor();

        String expectedJPGUrl = "https://covers.openlibrary.org/b/id/13257896-L.jpg";

        cue.getBookCover(OLID);
        String actualJPGUrl = cue.coverURL;

        Assertions.assertEquals(expectedJPGUrl,actualJPGUrl);
    }
}
