package home.yorku.bookmarks.controller;
import home.yorku.bookmarks.controller.database.ConnectionMethods;
import home.yorku.bookmarks.controller.search.BookSearchManager;
import home.yorku.bookmarks.controller.search.CoverUrlExtractor;
import home.yorku.bookmarks.controller.search.MovieSearchManager;
import home.yorku.bookmarks.controller.sorting.AlphaSort;
import home.yorku.bookmarks.model.*;
import javafx.event.EventHandler;
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
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
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
            "QA", "TA", "Client"
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
    @FXML
    private ListView<String> myBookList;
    private ObservableList<String> bookList = FXCollections.observableArrayList();
    @FXML
    private ListView<String> myMovieList;
    private ObservableList<String> movieList = FXCollections.observableArrayList();
    @FXML
    private ListView<String> ML_myBookList;
    private ObservableList<String> MLbookList = FXCollections.observableArrayList();
    @FXML
    private ListView<String> favourite_books;
    private ObservableList<String> MLfavBooks = FXCollections.observableArrayList();
    @FXML
    private ListView<String> ML_myMovieList;
    private ObservableList<String> MLmovieList = FXCollections.observableArrayList();
    @FXML
    private ListView<String> upNextList;
    private ObservableList<String> futureList = FXCollections.observableArrayList();
    @FXML
    private ImageView coverImageView;
    @FXML
    private Label description;
    private Set<Movie> MovieSet;
    private Set<Book> BookSet;
    private BookPortfolio bookPortfolio;
    private MoviePortfolio moviePortfolio;
    private double sceneHeight;
    private double sceneWidth;

    private boolean logout = false;

    public BookmarkController() {
    }

    @FXML
    private void initialize() { //Initializes all Listview items

        bookPortfolio = new BookPortfolio();
        moviePortfolio = new MoviePortfolio();
        //Initialize the list when null
        myBookList.setItems(bookList);
        myMovieList.setItems(movieList);
        ML_myBookList.setItems(MLbookList);
        ML_myMovieList.setItems(MLmovieList);
        upNextList.setItems(futureList);
        user.setItems(userOptions);

        //Update lists
        listUpdate();

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
                    MenuItem myCurrentList = menu.getItems().get(0);
                    MenuItem myFutureList = menu.getItems().get(1);
                    stackPane.getChildren().set(0, label);
                    setGraphic(stackPane);
                    // Call the "saveToList" function when Menu item 1 is clicked
                    myCurrentList.setOnAction(e -> saveToMyCurrentList());
                    myFutureList.setOnAction(e -> saveToMyFutureList());
                }
            }
        });
    }

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

                    if(url.getBookCover(b.getIsbn())){
                        InputStream stream = Files.newInputStream(Paths.get("./temporary.jpg"));
                        Image coverImage = new Image(stream);
                        coverImageView.setImage(coverImage);
                        coverImageView.setFitWidth(100);
                        coverImageView.setFitHeight(200);
                        description.setGraphic(coverImageView);
                    }else{
                        InputStream stream = Files.newInputStream(Paths.get("Images/book-placeholder.jpg"));
                        Image coverImage = new Image(stream);
                        coverImageView.setImage(coverImage);
                        coverImageView.setFitWidth(100);
                        coverImageView.setFitHeight(200);
                        description.setGraphic(coverImageView);

                    }


                }
                i++;
            }

        }

        System.out.println("clicked on " + myListView.getSelectionModel().getSelectedItem());

    }

    private void listUpdate(){
        ConnectionMethods method = new ConnectionMethods();
        this.bookPortfolio.getSavedBooks().clear();
        MLbookList.clear();
        //will need to update to handel is_favourite flag
        //Split to two Movie & book for later
        BookSet = method.pullBooks();

        for (Book b : BookSet) {
            this.bookPortfolio.AddToSavedBooks(b);
            MLbookList.add(b.getTitle());
        }

        ML_myBookList.setItems(MLbookList);
        myBookList.setItems(MLbookList);


        MLmovieList = FXCollections.observableList(method.pullMovies());
        ML_myMovieList.setItems(MLmovieList);
        myMovieList.setItems(MLmovieList);

        futureList = FXCollections.observableList(method.pullFutureList());
        upNextList.setItems(futureList);

        method.closeConnection();
        //BookSet.clear();
       // MLbookList.clear();

    }

    @FXML
    private void saveToMyFutureList(){

        ConnectionMethods method = new ConnectionMethods();

        final int selectedIndex = myListView.getSelectionModel().getSelectedIndex();

        if(searchType.getValue().equals("Books")){

            int i = 0;
            for (Book b : BookSet) {
                if(i == selectedIndex ){

                    method.insertFutureList(b.getIsbn(), "null" , user.getValue(), b.getIdentifier() , b.getTitle(), b.getAuthor().toString(), null, null);
                }
                i++;
            }


        }else if(searchType.getValue().equals("Movies")){

            int i = 0;
            for (Movie m : MovieSet) {
                if(i == selectedIndex ){

                    method.insertFutureList("null", String.valueOf(m.getId()), user.getValue(), m.getIdentifier(), m.getTitle(),  null, m.getReleaseDate(), m.getOverview());
                }
                i++;
            }

        }else{
            //error checking
        }
        listUpdate();
    }

    @FXML
    private void saveToMyCurrentList(){
        ConnectionMethods method = new ConnectionMethods();

        final int selectedIndex = myListView.getSelectionModel().getSelectedIndex();

        if(searchType.getValue().equals("Books")){

            int i = 0;
            for (Book b : BookSet) {
                if(i == selectedIndex ){

                    this.bookPortfolio.AddToSavedBooks(b);
                    method.insertBook(b.getIsbn(), user.getValue(), b.getIdentifier(), b.getTitle(), b.getAuthor().toString(), 0);

                }
                i++;
            }

        }else if(searchType.getValue().equals("Movies")){

            int i = 0;
            for (Movie m : MovieSet) {
                if(i == selectedIndex ){
                    this.moviePortfolio.AddToSavedMovies(m);
                    System.out.println("Title: " + m.getTitle() + "\n" + "Description: " + m.getOverview() + "\n" + "Release Date: " + m.getReleaseDate());
                    method.insertMovie(String.valueOf(m.getId()), user.getValue(), m.getIdentifier(), m.getTitle(), m.getReleaseDate(), m.getOverview(),0);
                }
                i++;
            }

        }else{
            System.out.println("Error at line 523: No searchType value");
        }

        listUpdate();
    }

    @FXML
    private void addBookToFavourites(ActionEvent event){
        //This implementation will be fixed itr 3 (favourites don't show up on list update)
        final String selectedItem = ML_myBookList.getSelectionModel().getSelectedItem();
        final int selectedIdx = ML_myBookList.getSelectionModel().getSelectedIndex();

        if(!selectedItem.startsWith("*")){
            ConnectionMethods method = new ConnectionMethods();
            method.addFavourite(selectedItem);
            ML_myBookList.getItems().remove(selectedIdx);
            MLbookList.add(0,"*" + selectedItem);
            bookList.add(0, "*" + selectedItem); //Pushes favourite items to top of the list
        }else{
            System.out.println("The Book is already in Favourites");
        }

       // listUpdate();
    //We will have to design this better for itr 3 as MovieSet is empty from ML
    /*
        int i = 0;
        for (Book b : BookSet) {
            if(i == selectedIdx ){
                this.bookPortfolio.AddToFavourites(b);
            }
            i++;
        }

     */
    }

    @FXML
    private void addMovieToFavourites(ActionEvent event){
        //This implementation will be fixed itr 3 (favourites don't show up on list update)
        final String selectedItem = ML_myMovieList.getSelectionModel().getSelectedItem();
        final int selectedIdx = ML_myMovieList.getSelectionModel().getSelectedIndex();

        if(!selectedItem.startsWith("*")){
            myMovieList.getItems().remove(selectedIdx);
            MLmovieList.add(0,"*" + selectedItem);
            movieList.add(0,"*" + selectedItem);//Pushes favourite items to top of the list
        }else{
            System.out.println("The Movies is already in Favourites");
        }

    //We will have to design this better for itr 3 as MovieSet is empty from ML
    /*
        int i = 0;
        for (Movie m : MovieSet) {
            if (i == selectedIdx) {
                this.moviePortfolio.AddToSavedMovies(m);
            }
            i++;
        }

     */
    }

    @FXML
    private void removeBook(ActionEvent event){
        //FIX "*" when favourite db is finished
        ConnectionMethods method = new ConnectionMethods();
        String selectedItem = ML_myBookList.getSelectionModel().getSelectedItem();
        final int selectedIdx = ML_myBookList.getSelectionModel().getSelectedIndex();

        if(selectedItem.startsWith("*")){
            selectedItem = selectedItem.substring(1);
        }

        if (selectedIdx != -1) {
            method.removeBook(selectedItem);
        }

    //We will have to design this better for itr 3 as MovieSet is empty from ML
    /*
        myBookList.getItems().remove(selectedIdx);

        int i = 0;
        for (Book b : BookSet) {
            if(i == selectedIdx ){
                this.bookPortfolio.RemoveFromSavedBooks(b);
            }
            i++;
        }

     */

        listUpdate();

    }

    @FXML
    private void removeMovie(ActionEvent event){

        ConnectionMethods method = new ConnectionMethods();
        String selectedItem = ML_myMovieList.getSelectionModel().getSelectedItem();
        final int selectedIdx = ML_myMovieList.getSelectionModel().getSelectedIndex();

        if(selectedItem.startsWith("*")){
            selectedItem = selectedItem.substring(1);
        }

        if (selectedIdx != -1) {
            method.removeMovie(selectedItem);
        }

    //We will have to design this better for itr 3 as MovieSet is empty from ML
    /*
        int i = 0;
        for (Movie m : MovieSet) {
            if(i == selectedIdx ) {
                this.moviePortfolio.AddToSavedMovies(m);
            }
            i++;
        }
    */

        listUpdate();

    }

    @FXML
    private void removeFutureList(ActionEvent event){
        ConnectionMethods method = new ConnectionMethods();

        String selectedItem = upNextList.getSelectionModel().getSelectedItem();
        final int selectedIdx = upNextList.getSelectionModel().getSelectedIndex();

        if (selectedIdx != -1) {
            method.removeFutureList(selectedItem);
        }

        listUpdate();
    }

    @FXML
    private void sortAlphaMovie(MouseEvent event){
        AlphaSort alphaSort = new AlphaSort();
        ArrayList<String> arrayList = new ArrayList<>();

        arrayList.addAll(ML_myMovieList.getItems());
        arrayList = alphaSort.sortMovies(arrayList);

        ML_myMovieList.getItems().removeAll(MLmovieList);
        ML_myMovieList.getItems().addAll(arrayList);

    }

    @FXML
    private void sortAlphaBook(MouseEvent event){
        AlphaSort alphaSort = new AlphaSort();
        ArrayList<String> arrayList = new ArrayList<>();

        arrayList.addAll(ML_myBookList.getItems());
        arrayList = alphaSort.sortMovies(arrayList);

        ML_myBookList.getItems().removeAll(MLbookList);
        ML_myBookList.getItems().addAll(arrayList);

    }


    public void login(MouseEvent mouseEvent) {

        ConnectionMethods method = new ConnectionMethods();

        if(!user.getValue().equals("Team:")){
            method.userLogin(user.getValue(), "Login");
            logout = false;
            stage = (Stage) tabPane.getScene().getWindow();
            stage.setWidth(900);
            stage.setHeight(680);
            listen();

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

    }

    public void logout(MouseEvent mouseEvent) {

        ConnectionMethods method = new ConnectionMethods();
        method.userLogin(user.getValue(), "Logout");
        logout = true;
        stage = (Stage) tabPane.getScene().getWindow();
        stage.setWidth(300);
        stage.setHeight(300);
        listen();

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

    public void listen() {
        ConnectionMethods method = new ConnectionMethods();
        stage = (Stage) tabPane.getScene().getWindow();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {

                if(!logout){
                    method.userLogin(user.getValue(), "Logout");
                }

            }
        });
    }

}