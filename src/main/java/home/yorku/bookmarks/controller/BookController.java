package home.yorku.bookmarks.controller;
import home.yorku.bookmarks.model.Book;
import home.yorku.bookmarks.model.Movie;
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

            String author = book.getAuthor().toString();
            String title = book.getTitle();

            if (author.length() > 30) {
                author = author.substring(0, 30) + "..."; // truncate to 30 characters
            }

            if(title.length() > 38){
                title = title.substring(0, 38) + "..."; // truncate to 30 characters
            }

            myListView.getItems().add(title + " | | " + "Author: " + author);
        }
    }


}
