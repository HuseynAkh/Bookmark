package home.yorku.bookmarks.controller;

import home.yorku.bookmarks.model.Book;
import home.yorku.bookmarks.model.Movie;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.Collections;
import java.util.Set;

public class RecoController {

    private Set<Book> books;
    private Set<Movie> movies;
    private ObservableList<String> recos;
    @FXML
    protected ListView<String> listView;

    public RecoController(Set<Book> books, Set<Movie> movies, ListView<String> listView, ObservableList<String> recos) {
        this.books = books;
        this.movies = movies;
        this.listView = listView;
        this.recos = recos;
    }

    protected void display(){

        listView.getItems().clear();
        recos.clear();

        for(Book b: books){

            String title = b.getTitle();
            String author = b.getAuthor().toString();
            author = author.substring(1, author.length() - 1);
            String type = b.getIdentifier();

            if (author.length() > 30) {
                author = author.substring(0, 30) + "..."; // truncate to 30 characters
            }

            if(title.length() > 38){
                title = title.substring(0, 38) + "..."; // truncate to 30 characters
            }

            recos.add(title + " || Author(s): " + author + " || " + type);
        }

        for(Movie m: movies){

            String title = m.getTitle();
            String releaseDate = m.getReleaseDate();
            String type = m.getIdentifier();

            if(title.length() > 50){
                title = title.substring(0, 50) + "..."; // truncate to 30 characters
            }

            recos.add(title + " || relese date: " + releaseDate + " || " + type);
        }

        Collections.shuffle(recos);
        listView.setItems(recos);

    }
}
