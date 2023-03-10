package home.yorku.bookmarks.controller;
import home.yorku.bookmarks.controller.database.ConnectionMethods;
import home.yorku.bookmarks.controller.search.BookSearchManager;
import home.yorku.bookmarks.controller.search.CoverUrlExtractor;
import home.yorku.bookmarks.controller.search.ImageDownloader;
import home.yorku.bookmarks.controller.search.MovieSearchManager;
import home.yorku.bookmarks.model.Book;
import home.yorku.bookmarks.model.BookmarkConstants;
import home.yorku.bookmarks.model.Movie;
import home.yorku.bookmarks.model.SearchCriteria;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.event.ActionEvent;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

import java.util.concurrent.atomic.AtomicReference;

public class BookmarkController {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TabPane tabPane;
    @FXML
    private Button Login;
    @FXML
    private VBox LoginBox;
    private Stage stage;
    List<Tab> removedTabs = new ArrayList<>();
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
    private ChoiceBox<String> user;
    private ObservableList<String> userOptions = FXCollections.observableArrayList(
            "QA/Dev Team", "Client"
    );
    @FXML
    private TextField searchText;
    @FXML
    private Label ErrorChecking;
    @FXML
    private Label LoginError;
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
    private double sceneHeight;
    private double sceneWidth;

    public BookmarkController() {
    }

    @FXML
    private void initialize() { //Initializes all Listview items
        myBookList.setItems(bookList);
        myMovieList.setItems(movieList);
        user.setItems(userOptions);

        Scene scene = anchorPane.getScene();
        if (scene != null) {
            sceneHeight = scene.getHeight();
            sceneWidth = scene.getWidth();
        }

        // All dynamic button layouts
        anchorPane.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.heightProperty().addListener((obs, oldHeight, newHeight) -> {
                    sceneHeight = newHeight.doubleValue();
                    LoginBox.setLayoutY((sceneHeight/2) - (20 + LoginBox.getHeight()/2)); // 20 for padding between boxes

                });

                newScene.widthProperty().addListener((obs, oldWidth, newWidth) -> {
                    sceneWidth = newWidth.doubleValue();
                    LoginBox.setLayoutX((sceneWidth/2) - (LoginBox.getWidth()/2));

                });


            }
        });

        for (Tab tab : tabPane.getTabs()) {
            if (!tab.getId().equals("LoginPane")) {
                tab.setDisable(true);
            }
        }

        Tab logoutTab = tabPane.getTabs().stream()
                .filter(tab -> tab.getId().equals("LogoutPane"))
                .findFirst()
                .orElse(null);
        removedTabs.add(logoutTab);
        tabPane.getTabs().remove(logoutTab);

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

    private void clear(){
        if(MovieSet != null){
            MovieSet.clear();
        }

        if(BookSet != null){
            BookSet.clear();
        }
        description.setText("");
        description.setGraphic(null);
    }

    @FXML
    private void onSearchButtonClick(ActionEvent event) {

        clear();
        searchString = searchText.getText();
        ErrorChecking.setTextFill(Color.WHITE);
        MovieSearchManager ms = new MovieSearchManager();
        BookSearchManager bs = new BookSearchManager();

        if(searchType.getValue().equals("Movies")){ // first drop down choice box
            SearchCriteria searchCriteria;
            switch (searchBy.getValue()) {
                case "Title": {
                    ErrorChecking.setText("Searching Movies by Title: " + searchString + "...");
                    //
                    searchCriteria = new SearchCriteria(
                            BookmarkConstants.TYPE_MOVIE,
                            BookmarkConstants.KEY_MOVIE_TITLE,
                            searchString);

                    MovieSearchManager search = new MovieSearchManager();
                    MovieSet  = search.searchMovie(searchCriteria);
                    MovieController movieController = new MovieController(BookSet, MovieSet, myListView);
                    movieController.display();

                    break;
                }
                case "Actor": {
                    ErrorChecking.setText("Searching Movies by Actor: " + searchString + "...");
                    //
                    searchCriteria = new SearchCriteria(
                            BookmarkConstants.TYPE_MOVIE,
                            BookmarkConstants.KEY_MOVIE_ACTOR,
                            searchString);

                    MovieSearchManager search = new MovieSearchManager();
                    MovieSet  = search.searchMovie(searchCriteria);
                    //MovieSet = ms.MovieByActor(searchString);
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

            SearchCriteria searchCriteria = null;
            switch (searchBy.getValue()) {
                case "Title": {
                    ErrorChecking.setText("Searching Books by Title: " + searchString + "...");
                    searchCriteria = new SearchCriteria(
                            BookmarkConstants.TYPE_BOOK,
                            BookmarkConstants.KEY_BOOK_NAME,
                            searchString);

                    BookSearchManager bookSearch = new BookSearchManager();
                    BookSet = bookSearch.searchBook(searchCriteria);

                    BookController bookController = new BookController(BookSet, MovieSet, myListView);
                    bookController.display();

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
                    BookSet = bookSearch.searchBook(searchCriteria);

                    BookController bookController = new BookController(BookSet, MovieSet, myListView);
                    bookController.display();

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
                    BookSet = bookSearch.searchBook(searchCriteria);

                    BookController bookController = new BookController(BookSet, MovieSet, myListView);
                    bookController.display();

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

    protected String getType(){

        String type = "";
        final int selectedIndex = myListView.getSelectionModel().getSelectedIndex();

        if(MovieSet!=null){
            int i = 0;
            for (Movie m : MovieSet) {
                if(i == selectedIndex ){
                    type = m.getIdentifier();
                }
                i++;
            }
        }

        if(BookSet!=null){
            int i = 0;
            for (Book b : BookSet) {
                if(i == selectedIndex ){
                    type = b.getIdentifier();
                }
                i++;
            }
        }

        return type;

    }

    @FXML
    private void buttonControl(MouseEvent event){

        AtomicReference<ContextMenu> currentMenu = new AtomicReference<>(null);

        myListView.setCellFactory(lv -> new ListCell<String>() {
            private final Label button = new Label("Save to:");
            //May need to change and customize for specific list
            private final ContextMenu menu = new ContextMenu(new MenuItem("My Watched/Read List"), new MenuItem("Want to Watch/Read"));
            private final StackPane stackPane = new StackPane();

            {
                button.setVisible(false);
                stackPane.setAlignment(Pos.CENTER_LEFT);
                StackPane.setAlignment(button, Pos.CENTER_RIGHT);
                stackPane.getChildren().addAll(new Label(), button);
                button.setOnMouseEntered(e -> button.setUnderline(true));
                button.setOnMouseExited(e -> button.setUnderline(false));

                setOnMouseEntered(e -> {
                    if (currentMenu.get() == null) {
                        button.setVisible(true);
                    }
                });
                setOnMouseExited(e -> {
                    if (currentMenu.get() == null) {
                        button.setVisible(false);
                    }
                });
                button.setOnMouseClicked(e -> {
                    currentMenu.set(menu);
                    menu.show(button, Side.RIGHT, 0, 0);
                    e.consume();
                });
                menu.setOnHidden(e -> {
                    currentMenu.set(null);
                    button.setVisible(false);
                });

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
    private ImageView coverImageView;
    @FXML
    private void callDescription(MouseEvent event) throws IOException {

        final int selectedIndex = myListView.getSelectionModel().getSelectedIndex();

        description.setText("");//Clear the descriptions
        description.setGraphic(null);
        System.out.println(getType());

        if(getType().equals("Movie")){

            int i = 0;
            for (Movie m : MovieSet) {
                if(i == selectedIndex ){
                    description.setText(m.getOverview());
                    description.setPadding(new Insets(5, 5, 5, 5));
                }
                i++;
            }
        }
        if(getType().equals("Book")){
            CoverUrlExtractor url = new CoverUrlExtractor();

            int i = 0;
            for (Book b : BookSet) {
                if(i == selectedIndex ){
                    url.getBookCover(b.getIsbn());
                    InputStream stream = Files.newInputStream(Paths.get("./temporary.jpg"));
                    Image coverImage = new Image(stream);
                    coverImageView.setImage(coverImage);
                    coverImageView.setFitWidth(100);
                    coverImageView.setFitHeight(200);
                    description.setGraphic(coverImageView);
                }
                i++;
            }


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

    public void login(MouseEvent mouseEvent) {

        /*if(!user.getValue().equals("Team:")){
            stage = (Stage) tabPane.getScene().getWindow();
            stage.setWidth(900);
            stage.setHeight(680);

            tabPane.getTabs().addAll(removedTabs);
            removedTabs.clear();

            for (Tab tab : tabPane.getTabs()) {
                tab.setDisable(false);
            }

            // Enable and show the login tab
            Tab loginTab = tabPane.getTabs().stream()
                    .filter(tab -> tab.getId().equals("LoginPane"))
                    .findFirst()
                    .orElse(null);
            removedTabs.add(loginTab);
            tabPane.getTabs().remove(loginTab);
        }else{
            LoginError.setTextFill(Color.RED);
            LoginError.setText("Please select your Team to Login");
        }

         */
        stage = (Stage) tabPane.getScene().getWindow();
        stage.setWidth(900);
        stage.setHeight(680);

        tabPane.getTabs().addAll(removedTabs);
        removedTabs.clear();

        for (Tab tab : tabPane.getTabs()) {
            tab.setDisable(false);
        }

        // Enable and show the login tab
        Tab loginTab = tabPane.getTabs().stream()
                .filter(tab -> tab.getId().equals("LoginPane"))
                .findFirst()
                .orElse(null);
        removedTabs.add(loginTab);
        tabPane.getTabs().remove(loginTab);

    }

    public void logout(MouseEvent mouseEvent) {

        stage = (Stage) tabPane.getScene().getWindow();
        stage.setWidth(300);
        stage.setHeight(300);

        tabPane.getTabs().add(0,removedTabs.get(0));
        removedTabs.clear();

        for (Tab tab : tabPane.getTabs()) {
            if (!tab.getId().equals("LoginPane")) {
                tab.setDisable(true);
            }
        }

        // Enable and show the login tab
        Tab logoutTab = tabPane.getTabs().stream()
                .filter(tab -> tab.getId().equals("LogoutPane"))
                .findFirst()
                .orElse(null);
        removedTabs.add(logoutTab);
        tabPane.getTabs().remove(logoutTab);
        LoginError.setText("");

    }

}