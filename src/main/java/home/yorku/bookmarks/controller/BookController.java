package home.yorku.bookmarks.controller;
import home.yorku.bookmarks.model.Book;
import home.yorku.bookmarks.model.Movie;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.Set;

public class BookController {

    private Set<Book> books;
    @FXML
    private ListView<String> listView;

    protected BookController(Set<Book> books, ListView<String> listView) {
        this.books = books;
        this.listView = listView;
    }

    protected void display() {
        listView.getItems().clear();

        for (Book book : books) {

            String author = book.getAuthor().toString();
            author = author.substring(1, author.length() - 1);
            String title = book.getTitle();

            if (author.length() > 30) {
                author = author.substring(0, 30) + "..."; // truncate to 30 characters
            }

            if(title.length() > 38){
                title = title.substring(0, 38) + "..."; // truncate to 30 characters
            }

            listView.getItems().add(title + " || Author(s): " + author);
        }

    }


}
