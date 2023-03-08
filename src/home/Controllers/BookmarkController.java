package home.Controllers;
import home.Database.*;
import home.backend.*;
import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.geometry.Pos;
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
    private ListView<String> movieListView;

    @FXML
    private void initialize() { //Initializes all Listview items
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

        searchString = searchText.getText();
        ErrorChecking.setTextFill(Color.WHITE);
        MovieSearch ms = new MovieSearch();
        BookSearch bs = new BookSearch();

        if(searchType.getValue().equals("Movies")){ // first drop down choice box

            switch (searchBy.getValue()) {
                case "Title": {
                    ErrorChecking.setText("Searching Movies by Title: " + searchString + "...");

                    if(MovieSet != null){
                        MovieSet.clear();
                    }

                    MovieSet = ms.MovieByTitle(searchString);
                    MovieController movieController = new MovieController(BookSet, MovieSet, myListView);
                    movieController.display();

                    break;
                }
                case "Actor": {
                    ErrorChecking.setText("Searching Movies by Actor: " + searchString + "...");

                    if(MovieSet != null){
                        MovieSet.clear();
                    }

                    MovieSet = ms.MovieByActor(searchString);
                    MovieController movieController = new MovieController(BookSet, MovieSet, myListView);
                    movieController.display();

                    break;
                }
                default:
                    ErrorChecking.setTextFill(Color.RED);
                    ErrorChecking.setText("Please choose a selection from the drop down title \"Search by\" ");
                    break;
            }

        }else if (searchType.getValue().equals("Books")){

            switch (searchBy.getValue()) {
                case "Title": {
                    ErrorChecking.setText("Searching Books by Title: " + searchString + "...");

                    if(BookSet != null){
                        BookSet.clear();
                    }

                    BookSet = bs.searchBookName(searchString);
                    BookController bookController = new BookController(BookSet, MovieSet, myListView);
                    bookController.display();

                    break;
                }
                case "Genre": {
                    ErrorChecking.setText("Searching Books by Genre: " + searchString + "...");

                    if(BookSet != null){
                        BookSet.clear();
                    }

                    BookSet = bs.SearchBookGenre(searchString);
                    BookController bookController = new BookController(BookSet, MovieSet, myListView);
                    bookController.display();

                    break;
                }
                case "Author": {
                    ErrorChecking.setText("Searching Books by Author: " + searchString + "...");

                    if(BookSet != null){
                        BookSet.clear();
                    }


                    BookSet = bs.searchBookAuthor(searchString);
                    System.out.println(BookSet);
                   // BookController bookController = new BookController(BookSet, MovieSet, myListView);
                  //  bookController.display();

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
    private void buttonControl(MouseEvent event){
        myListView.setCellFactory(lv -> new ListCell<String>() {
            private final Label button = new Label("Save to:");
            private final ContextMenu menu = new ContextMenu(new MenuItem("MyList"), new MenuItem("Menu item 2"));
            private final StackPane stackPane = new StackPane();

            {
                // Hide the button initially
                button.setVisible(false);
                stackPane.setAlignment(Pos.CENTER_LEFT);
                // Align the button to the right border of the cell
                StackPane.setAlignment(button, Pos.CENTER_RIGHT);
                stackPane.getChildren().addAll(new Label(), button);
                // Show the button when the cell is hovered over
                setOnMouseEntered(e -> button.setVisible(true));
                // Hide the button when the mouse leaves the cell
                setOnMouseExited(e -> {
                    if (!menu.isShowing()) {
                        button.setVisible(false);
                    }
                });
                // Underline the button when the mouse hovers over it
                button.setOnMouseEntered(e -> button.setUnderline(true));
                button.setOnMouseExited(e -> button.setUnderline(false));
                // Set the autoHide property of the menu to false
                menu.setAutoHide(false);
                menu.setHideOnEscape(false);
                menu.setOnHidden(e -> button.setVisible(false));
                // Show the menu when the button is clicked
                button.setOnMouseClicked(e -> menu.show(button, Side.RIGHT, 0, 0));

            }

            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    Label label = new Label(item);
                    MenuItem bookList = menu.getItems().get(0);
                    stackPane.getChildren().set(0, label);
                    setGraphic(stackPane);

                    // Call the "saveToList" function when Menu item 1 is clicked
                    bookList.setOnAction(e -> saveToList());
                }
            }
        });
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
    private void saveToList(){
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