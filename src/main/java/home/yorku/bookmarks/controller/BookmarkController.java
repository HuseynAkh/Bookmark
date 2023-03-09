package home.yorku.bookmarks.controller;
import home.yorku.bookmarks.controller.database.ConnectionMethods;
import home.yorku.bookmarks.controller.search.BookSearchManager;
import home.yorku.bookmarks.controller.search.MovieSearchManager;
import home.yorku.bookmarks.model.Book;
import home.yorku.bookmarks.model.BookmarkConstants;
import home.yorku.bookmarks.model.Movie;
import home.yorku.bookmarks.model.SearchCriteria;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Set;

public class BookmarkController {

    @FXML
    private ChoiceBox<String> searchType;
    private ObservableList<String> moviesSearchOptions = FXCollections.observableArrayList(
            "Title", "Actor"
    );
    @FXML
    private ChoiceBox<String> searchBy;

    private ObservableList<String> booksSearchOptions = FXCollections.observableArrayList(
            "Title", "Genre", "Author"
    );
    @FXML
    private TextField searchText;
    @FXML
    private Label ErrorChecking;
    private String searchString = "";
    @FXML
    private ListView<String> myListView;
    private ObservableList<String> items = FXCollections.observableArrayList();
    @FXML
    private ListView<String> myBookList;
    private ObservableList<String> bookList = FXCollections.observableArrayList();
    @FXML
    private ListView<String> myMovieList;
    private ObservableList<String> movieList = FXCollections.observableArrayList();
    @FXML
    private Label description;
    private Set<Movie> MovieSet;
    private Set<Book> BookSet;

    @FXML
    private void initialize() { //Initializes all Listview items
        myListView.setItems(items);
        myBookList.setItems(bookList);
        myMovieList.setItems(movieList);

        searchType.setOnAction(event -> {
            if (searchType.getValue().equals("Movies")) {
                searchBy.setItems(moviesSearchOptions);
                searchBy.setValue("Search by");
            } else if (searchType.getValue().equals("Books")) {
                searchBy.setItems(booksSearchOptions);
                searchBy.setValue("Search by");
            }
        });

    }

    @FXML
    private void onSearchButtonClick(ActionEvent event) {

        items.clear();
        searchString = searchText.getText();
        ErrorChecking.setTextFill(Color.WHITE);
        MovieSearchManager ms = new MovieSearchManager();
        BookSearchManager bs = new BookSearchManager();

        if(searchType.getValue().equals("Movies")){ // first drop down choice box
            SearchCriteria searchCriteria;
            switch (searchBy.getValue()) {
                case "Title": {
                    ErrorChecking.setText("Searching Movies by Title: " + searchString + "...");

                    if(MovieSet != null){
                        MovieSet.clear();
                    }
                    //
                    searchCriteria = new SearchCriteria(
                            BookmarkConstants.TYPE_MOVIE,
                            BookmarkConstants.KEY_MOVIE_TITLE,
                            searchString);

                    MovieSearchManager search = new MovieSearchManager();
                    MovieSet  = search.searchMovie(searchCriteria);
                    //
                    //MovieSet = ms.MovieByTitle(searchString);
                    for (Movie m : MovieSet) {
                        items.add(m.getTitle());
                    }
                    break;
                }
                case "Actor": {
                    ErrorChecking.setText("Searching Movies by Actor: " + searchString + "...");

                    if(MovieSet != null){
                        MovieSet.clear();
                    }
                    //
                    searchCriteria = new SearchCriteria(
                            BookmarkConstants.TYPE_MOVIE,
                            BookmarkConstants.KEY_MOVIE_ACTOR,
                            searchString);

                    MovieSearchManager search = new MovieSearchManager();
                    MovieSet  = search.searchMovie(searchCriteria);
                    //

                    //MovieSet = ms.MovieByActor(searchString);
                    for (Movie m : MovieSet) {
                        items.add(m.getTitle());
                    }
                    break;
                }
                default:
                    ErrorChecking.setTextFill(Color.RED);
                    ErrorChecking.setText("Please choose a selection from the drop down title \"Search by\" ");
                    break;
            }

        }else if (searchType.getValue().equals("Books")){

            SearchCriteria searchCriteria = null;
            switch (searchBy.getValue()) {
                case "Title": {
                    ErrorChecking.setText("Searching Books by Title: " + searchString + "...");
                    searchCriteria = new SearchCriteria(
                            BookmarkConstants.TYPE_BOOK,
                            BookmarkConstants.KEY_BOOK_NAME,
                            searchString);

                    BookSearchManager bookSearch = new BookSearchManager();
                    Set<Book> arr = bookSearch.searchBook(searchCriteria);

                    //Set<Book> arr = bs.searchBookName(searchString);
                    for (Book b : arr) {
                        items.add(b.getTitle());
                    }
                    break;
                }
                case "Genre": {
                    ErrorChecking.setText("Searching Books by Genre: " + searchString + "...");
                    searchCriteria = new SearchCriteria(
                            BookmarkConstants.TYPE_BOOK,
                            BookmarkConstants.KEY_BOOK_GENRE,
                            searchString);

                    //Set<Book> arr = bs.SearchBookGenre(searchString);
                    BookSearchManager bookSearch = new BookSearchManager();
                    Set<Book> arr = bookSearch.searchBook(searchCriteria);

                    for (Book b : arr) {
                        items.add(b.getTitle());
                        //System.out.println("clicked on " + b.getTitle());
                    }
                    break;
                }
                case "Author": {
                    ErrorChecking.setText("Searching Books by Author: " + searchString + "...");
                    searchCriteria = new SearchCriteria(
                            BookmarkConstants.TYPE_BOOK,
                            BookmarkConstants.KEY_BOOK_AUTHOR,
                            searchString);

                    //Set<Book> arr = bs.SearchBookGenre(searchString);
                    BookSearchManager bookSearch = new BookSearchManager();
                    Set<Book> arr = bookSearch.searchBook(searchCriteria);

                    //Set<Book> arr = bs.searchBookAuthor(searchString);
                    for (Book b : arr) {
                        items.add(b.getTitle());
                    }
                    break;
                }
                default:
                    ErrorChecking.setTextFill(Color.RED);
                    ErrorChecking.setText("Please choose a selection from the drop down title \"Search by\" ");
                    break;
            }
        }else {

            ErrorChecking.setTextFill(Color.RED);
            ErrorChecking.setText("Please choose a selection from the drop down title \"Type\" and \"Search by\" ");

        }
    }

    @FXML
    private void callDescription(MouseEvent event){

        if(searchType.getValue().equals("Movies")){
            description.setText("");//Clear the descriptions
            final int selectedIndex = myListView.getSelectionModel().getSelectedIndex();
            int i = 0;
            for (Movie m : MovieSet) {
                if(i == selectedIndex ){
                    description.setText(m.getOverview());
                    description.setPadding(new Insets(5, 5, 5, 5));
                }
                i++;
            }
        }
        if(searchType.getValue().equals("Books")){
            description.setText("");//Clear the description
        }

        System.out.println("clicked on " + myListView.getSelectionModel().getSelectedItem());

    }


    //Need to fix bug where if Book is selected from drop down after a Movie search,
    //it will add the book to the movie list
    //May need to wait for backend to return the object so that we can have a "check for book" bool

    @FXML
    private void saveToList(ActionEvent event){
        ConnectionMethods method = new ConnectionMethods();

        final String selectedItem = myListView.getSelectionModel().getSelectedItem();
        final int selectedIndex = myListView.getSelectionModel().getSelectedIndex();

        if(searchType.getValue().equals("Books")){

            method.insertBook(01, selectedItem, "Anonymous", "NULL", "This will be the book description");
            bookList.add(selectedItem);

        }else if(searchType.getValue().equals("Movies")){

            int i = 0;
            for (Movie m : MovieSet) {
                if(i == selectedIndex ){
                    System.out.println("Title: " + m.getTitle() + "\n" + "Description: " + m.getOverview() + "\n" + "Release Date: " + m.getReleaseDate());
                    method.insertMovie(01, m.getTitle(), m.getReleaseDate(), m.getOverview() );
                }
                i++;
            }
            movieList.add(selectedItem);
        }else{
            //error checking
        }
    }

    @FXML
    private void addBookToFavourites(ActionEvent event){
        final String selectedItem = myBookList.getSelectionModel().getSelectedItem();
        final int selectedIdx = myBookList.getSelectionModel().getSelectedIndex();

        if(!selectedItem.startsWith("*")){
            myBookList.getItems().remove(selectedIdx);
            bookList.add(0, "*" + selectedItem); //Pushes favourite items to top of the list
        }else{
            System.out.println("The Book is already in Favourites");
        }

    }

    @FXML
    private void addMovieToFavourites(ActionEvent event){
        final String selectedItem = myMovieList.getSelectionModel().getSelectedItem();
        final int selectedIdx = myMovieList.getSelectionModel().getSelectedIndex();

        if(!selectedItem.startsWith("*")){
            myMovieList.getItems().remove(selectedIdx);
            movieList.add(0,"*" + selectedItem);//Pushes favourite items to top of the list
        }else{
            System.out.println("The Movies is already in Favourites");
        }
    }

    @FXML
    private void removeBook(ActionEvent event){

        final int selectedIdx = myBookList.getSelectionModel().getSelectedIndex();
        if (selectedIdx != -1) {
            myBookList.getItems().remove(selectedIdx);
        }
    }

    @FXML
    private void removeMovie(ActionEvent event){
        final int selectedIdx = myMovieList.getSelectionModel().getSelectedIndex();
        if (selectedIdx != -1) {
            myMovieList.getItems().remove(selectedIdx);
        }
    }

}