package home.Controllers;
import home.backend.*;
import javafx.scene.control.ListView;

import java.util.Set;
public class MovieController extends MediaController {

    public MovieController(Set<Book> books, Set<Movie> movies, ListView<String> listView) {
        super(books, movies, listView);
    }

    @Override
    protected void display() {
        myListView.getItems().clear();
        for (Movie movie : movies) {
            myListView.getItems().add(movie.getTitle());
        }
    }

}
