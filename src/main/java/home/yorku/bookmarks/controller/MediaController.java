package home.yorku.bookmarks.controller;
import home.yorku.bookmarks.model.Book;
import home.yorku.bookmarks.model.Movie;
import javafx.scene.control.ListView;
import javafx.fxml.FXML;
import java.util.Set;

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
