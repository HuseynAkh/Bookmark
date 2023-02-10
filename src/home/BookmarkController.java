package home;
import home.backend.Book;
import home.backend.BookSearch;
import home.backend.Movie;
import home.backend.MovieSearch;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import java.util.Set;

public class BookmarkController {
    @FXML
    private Label searchingText;

    @FXML
    private ChoiceBox<String> firstChoiceBox;
    private String searchType = ""; //Movies or Books

    @FXML
    private ChoiceBox<String> secondChoiceBox;
    private String searchBy = ""; //Genre, Actor etc.

    @FXML
    private TextField searchText;
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
    private void initialize() { //Initializes all Listview items
        myListView.setItems(items);
        myBookList.setItems(bookList);
        myMovieList.setItems(movieList);
    }

    @FXML
    private void onSearchButtonClick(ActionEvent event) {

        items.clear();
        searchString = searchText.getText();
        searchingText.setTextFill(Color.WHITE);
        MovieSearch ms = new MovieSearch();
        BookSearch bs = new BookSearch();

        if(searchType.equals("Movies")){ // first Pain

            if(searchBy.equals("Movie-Search by Title")){
                searchingText.setText("Searching Movies by Title: " + searchString + "...");

                Set<Movie> arr = ms.MovieByTitle(searchString);
                for(Movie m : arr){
                    items.add(m.getTitle());
                }
            }else if(searchBy.equals("Movie-Search by Actor")){
                searchingText.setText("Searching Movies by Actor: " + searchString + "...");
                Set<Movie> arr = ms.MovieByActor(searchString);
                for(Movie m : arr){
                    items.add(m.getTitle());
                }

            }else{
                searchingText.setTextFill(Color.RED);
                searchingText.setText("Please choose a \"Movie-\" selection from the drop down title \"Search by\" ");
            }

        }else if (searchType.equals("Books")){

            if(searchBy.equals("Book-Search by Title")){
                searchingText.setText("Searching Books by Title: " + searchString + "...");

                Set<Book> arr = bs.searchBookName(searchString);
                for(Book b : arr){
                    items.add(b.getTitle());
                }
            }else if(searchBy.equals("Book-Search by Genre")){
                searchingText.setText("Searching Books by Genre: " + searchString +  "...");

                Set<Book> arr = bs.SearchBookGenre(searchString);
                for(Book b : arr){
                    items.add(b.getTitle());
                }
            }else if (searchBy.equals("Book-Search by Author")){
                searchingText.setText("Searching Books by Author: " + searchString + "...");

                Set<Book> arr = bs.searchBookAuthor(searchString);
                for(Book b : arr){
                    items.add(b.getTitle());
                }
            }else{
                searchingText.setTextFill(Color.RED);
                searchingText.setText("Please choose a \"Book-\" selection from the drop down title \"Search by\" ");
            }
        }else {

            searchingText.setTextFill(Color.RED);
            searchingText.setText("Please choose a selection from the drop down title \"Type\" and \"Search by\" ");

        }
    }

    @FXML
    private void onChoiceClick(ActionEvent event){
        searchType = firstChoiceBox.getValue();
    }

    @FXML
    private void onSecondChoiceClick(ActionEvent event){
        searchBy = secondChoiceBox.getValue();
    }

    @FXML
    private void saveToList(ActionEvent event){

        final String selectedItem = myListView.getSelectionModel().getSelectedItem();
        if(searchType.equals("Books")){
            bookList.add(selectedItem);
        }else if(searchType.equals("Movies")){
            movieList.add(selectedItem);
        }else{
            //error checking
        }
    }

    @FXML
    private void addBookToFavourites(ActionEvent event){
        final String selectedItem = myBookList.getSelectionModel().getSelectedItem();
        final int selectedIdx = myBookList.getSelectionModel().getSelectedIndex();
        myBookList.getItems().remove(selectedIdx);
        bookList.add(0, "*" + selectedItem); //Pushes favourite items to top of the list

    }

    @FXML
    private void addMovieToFavourites(ActionEvent event){
        final String selectedItem = myMovieList.getSelectionModel().getSelectedItem();
        final int selectedIdx = myMovieList.getSelectionModel().getSelectedIndex();
        myMovieList.getItems().remove(selectedIdx);
        movieList.add(0,"*" + selectedItem);//Pushes favourite items to top of the list
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