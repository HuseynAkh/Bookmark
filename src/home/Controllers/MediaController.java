package home.Controllers;
import home.backend.*;
import java.util.Set;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public abstract class MediaController {
    protected Set<Book> books;
    protected Set<Movie> movies;
    @FXML
    protected ListView<String> myListView;

    public MediaController(Set<Book> books, Set<Movie> movies, ListView<String> listView) {
        this.books = books;
        this.movies = movies;
        myListView = listView;
    }

    protected abstract void display();

}
