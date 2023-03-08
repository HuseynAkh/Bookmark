package home.Controllers;
import home.backend.*;
import javafx.scene.control.ListView;

import java.util.Set;

public class BookController extends MediaController {

    public BookController(Set<Book> books, Set<Movie> movies, ListView<String> listView) {
        super(books, movies, listView);
    }

    @Override
    protected void display() {
        myListView.getItems().clear();
        for (Book book : books) {
            myListView.getItems().add(book.getTitle());
        }
    }

}
