package home.yorku.bookmarks.controller;
import home.yorku.bookmarks.model.Book;
import home.yorku.bookmarks.model.Movie;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.Set;
public class MovieController {

    private Set<Movie> movies;
    @FXML
    private ListView<String> listView;

    protected MovieController(Set<Movie> movies, ListView<String> listView) {
        this.movies = movies;
        this.listView = listView;
    }

    protected void display() {
        listView.getItems().clear();

        for (Movie movie : movies) {

            String releaseDate = movie.getReleaseDate();
            String title = movie.getTitle();

            if(title.length() > 50){
                title = title.substring(0, 50) + "..."; // truncate to 30 characters
            }
            listView.getItems().add(title + " || release date: " + releaseDate);
        }


    }

}

