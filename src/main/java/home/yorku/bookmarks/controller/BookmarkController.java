package home.yorku.bookmarks.controller;

import home.yorku.bookmarks.controller.database.ConnectionMethods;
import home.yorku.bookmarks.controller.DatabaseController;
import home.yorku.bookmarks.controller.recommendation.recommendation;
import home.yorku.bookmarks.controller.search.BookSearchManager;
import home.yorku.bookmarks.controller.search.CoverUrlExtractor;
import home.yorku.bookmarks.controller.search.MovieSearchManager;
import home.yorku.bookmarks.controller.sorting.AlphaSort;
import home.yorku.bookmarks.model.*;
import javafx.animation.PauseTransition;
//import javafx.event.Event;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.event.ActionEvent;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class BookmarkController {
    @FXML
    protected AnchorPane anchorPane;
    @FXML
    protected TabPane tabPane;
    @FXML
    protected VBox LoginBox;
    @FXML
    protected VBox RecoBox;
    @FXML
    protected Tab FavouriteBookTab;
    @FXML
    protected Tab FavouriteMovieTab;
    @FXML
    protected Button removeBookFavBtn;
    @FXML
    protected Button removeBook;
    @FXML
    protected Button addBookToFav;
    @FXML
    protected Button bookSort;
    @FXML
    protected Button removeMovieFavBtn;
    @FXML
    protected Button removeMovie;
    @FXML
    protected Button addMovieToFav;
    @FXML
    protected Button movieSort;
    @FXML
    protected ChoiceBox<String> searchType;
    @FXML
    protected ChoiceBox<String> searchBy;
    @FXML
    protected ChoiceBox<String> sortReco;
    @FXML
    private TextField searchText;
    @FXML
    protected TextField usernameTxt;
    @FXML
    protected PasswordField passwordTxt;
    @FXML
    private Label ErrorChecking;
    @FXML
    protected Label LoginError;
    @FXML
    protected Label titleLabel;
    @FXML
    protected Label idLabel;
    @FXML
    protected Label authorOrRelease;
    @FXML
    protected Label descLabel;
    @FXML
    protected Label desc;
    @FXML
    protected ImageView coverImageView;
    @FXML
    private ListView<String> myListView;
    @FXML
    protected ListView<String> myBookList;
    protected final ObservableList<String> bookList = FXCollections.observableArrayList();
    @FXML
    protected ListView<String> myMovieList;
    protected final ObservableList<String> movieList = FXCollections.observableArrayList();
    @FXML
    protected ListView<String> ML_myBookList;
    protected final ObservableList<String> MLbookList = FXCollections.observableArrayList();
    @FXML
    protected ListView<String> favourite_books;
    protected final ObservableList<String> MLfavBooks = FXCollections.observableArrayList();
    @FXML
    protected ListView<String> ML_myMovieList;
    protected final ObservableList<String> MLmovieList = FXCollections.observableArrayList();
    @FXML
    protected ListView<String> favourite_movies;
    protected final ObservableList<String> MLfavMovies = FXCollections.observableArrayList();
    @FXML
    protected ListView<String> upNextList;
    protected ObservableList<String> futureList = FXCollections.observableArrayList();
    @FXML
    protected ListView<String> recommendation;
    protected ObservableList<String> recos = FXCollections.observableArrayList();
    protected Stage stage;
    protected String validUserId = "";
    private String searchString = "";
    private String myList = "";
    private String upNext = "";
    private Set<Book> BookSet;
    private Set<Movie> MovieSet;
    protected double sceneHeight;
    protected double sceneWidth;
    protected boolean logout = false;
    protected List<Tab> removedTabs = new ArrayList<>();
    protected final ObservableList<String> moviesSearchOptions = FXCollections.observableArrayList(
            "Title", "Actor"
    );
    protected final ObservableList<String> booksSearchOptions = FXCollections.observableArrayList(
            "Title", "Genre", "Author"
    );
    private DecsriptionController description;
    private PortfolioController portfolio;
    private DatabaseController database;
    private InitController initialize;
    private LoginController login;
    private SortController sort;

    public BookmarkController() {

        description = new DecsriptionController(this);
        portfolio = new PortfolioController(this);
        initialize = new InitController(this);
        login = new LoginController(this);
        sort = new SortController(this);
        database = new DatabaseController(this, portfolio);

    }

    // Initializes all listviews, dynamic buttons, tab views on login,
    // book/movie portfolio's for each user session to manipulate during
    // run time
    @FXML
    private void initialize() {
        initialize.initialize();
    }

    // Used to clear the observable Book and Movie set returned from the search
    // Is used when the user starts a new search
    private void clearSet() {
        if (MovieSet != null) {
            MovieSet.clear();
        }

        if (BookSet != null) {
            BookSet.clear();
        }
    }

    // Responsible for calling the specific search managers based on user filers
    // and populating the Book and Movie observable list with the returned results from the
    // manager
    @FXML
    private void onSearchButtonClick(ActionEvent event) {

        clearSet();
        description.clear();
        searchString = searchText.getText();
        ErrorChecking.setTextFill(Color.WHITE);

        if (searchType.getValue().equals("Movies")) { // first drop down choice box

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
                    MovieSet = search.searchMovie(searchCriteria);
                    MovieController movieController = new MovieController(MovieSet, myListView);
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
                    MovieSet = search.searchMovie(searchCriteria);
                    MovieController movieController = new MovieController(MovieSet, myListView);
                    movieController.display();

                    break;
                }
                default:
                    ErrorChecking.setTextFill(Color.RED);
                    ErrorChecking.setText("Please choose a selection from the drop down title \"Search by\" ");
                    break;
            }

            myList = "Movies I've watched";
            upNext = "Movies I want to watch";

        } else if (searchType.getValue().equals("Books")) {

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

                    BookController bookController = new BookController(BookSet, myListView);
                    bookController.display();

                    break;
                }
                case "Genre": {
                    ErrorChecking.setText("Searching Books by Genre: " + searchString + "...");
                    searchCriteria = new SearchCriteria(
                            BookmarkConstants.TYPE_BOOK,
                            BookmarkConstants.KEY_BOOK_GENRE,
                            searchString);

                    BookSearchManager bookSearch = new BookSearchManager();
                    BookSet = bookSearch.searchBook(searchCriteria);

                    BookController bookController = new BookController(BookSet, myListView);
                    bookController.display();

                    break;
                }
                case "Author": {
                    ErrorChecking.setText("Searching Books by Author: " + searchString + "...");
                    searchCriteria = new SearchCriteria(
                            BookmarkConstants.TYPE_BOOK,
                            BookmarkConstants.KEY_BOOK_AUTHOR,
                            searchString);

                    BookSearchManager bookSearch = new BookSearchManager();
                    BookSet = bookSearch.searchBook(searchCriteria);

                    BookController bookController = new BookController(BookSet, myListView);
                    bookController.display();

                    break;
                }
                default:
                    ErrorChecking.setTextFill(Color.RED);
                    ErrorChecking.setText("Please choose a selection from the drop down title \"Search by\" ");
                    break;
            }

            myList = "Books I've read";
            upNext = "Books I want to read";

        } else {
            ErrorChecking.setTextFill(Color.RED);
            ErrorChecking.setText("Please choose a selection from the drop down title \"Type\" and \"Search by\" ");
        }

    }

    // Responsible for the "Save to:" dropdown button which adds Books or Movies to the respective
    // My Book/Movie list OR Future Book/Movie list
    @FXML
    private void buttonControl() {

        myListView.setCellFactory(lv -> new ListCell<String>() {

            private final StackPane stackPane = new StackPane();
            private final ComboBox<String> comboBox = new ComboBox<>();
            private final PauseTransition delay = new PauseTransition(Duration.millis(150));
            private ComboBox<String> activeComboBox = null;

            {
                comboBox.setVisible(false);
                comboBox.getItems().addAll(myList, upNext);
                comboBox.setValue("Save to:");
                comboBox.setPrefWidth(85);
                stackPane.setAlignment(Pos.CENTER_LEFT);
                StackPane.setAlignment(comboBox, Pos.CENTER_RIGHT);
                stackPane.getChildren().addAll(new Label(), comboBox);

                setOnMouseEntered(e -> {
                    if (activeComboBox != null && activeComboBox != comboBox) {
                        comboBox.setVisible(false);
                    }
                    // Start the delay before showing the ComboBox
                    delay.setOnFinished(event -> {
                        comboBox.setVisible(true);
                        activeComboBox = comboBox;
                    });
                    delay.playFromStart();
                });

                setOnMouseExited(e -> {
                    if (!isHover() && activeComboBox != null) {
                        Bounds bounds = activeComboBox.localToScene(activeComboBox.getBoundsInLocal());
                        if (!bounds.contains(e.getSceneX(), e.getSceneY())) {
                            activeComboBox.setVisible(false);
                            activeComboBox = null;
                        }
                    }

                    delay.stop();
                });

                comboBox.showingProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue && !comboBox.getSelectionModel().isEmpty()) {
                        // Call the "saveToList" function when an item is selected from the ComboBox
                        int listViewIndex = getListView().getItems().indexOf(getItem());
                        int selectedIndex = comboBox.getSelectionModel().getSelectedIndex();

                        if (selectedIndex == 0) {
                            saveToMyCurrentList(listViewIndex);
                        } else if (selectedIndex == 1) {
                            saveToMyFutureList(listViewIndex);
                        }
                    }
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
                    stackPane.getChildren().set(0, label);
                    setGraphic(stackPane);
                }
            }
        });
    }

    // Responsible for getting the type "Book" or "Movie" of the selected item (from the user)
    // Is used to determine which list to add the selected item to "My Book/Movie list"
    protected String getType(int selectedIndex) {

        String type = "";

        if (MovieSet != null) {
            int i = 0;
            for (Movie m : MovieSet) {
                if (i == selectedIndex) {
                    type = m.getIdentifier();
                }
                i++;
            }
        }

        if (BookSet != null) {
            int i = 0;
            for (Book b : BookSet) {
                if (i == selectedIndex) {
                    type = b.getIdentifier();
                }
                i++;
            }
        }

        return type;
    }

    // Updates the description view box with the description of the movie
    // If the selection is a book it will load the cover image of the book
    @FXML
    private void callDescription() throws IOException {

        final int selectedIndex = myListView.getSelectionModel().getSelectedIndex();
        String type = getType(selectedIndex);
        description.clear();
        description.descriptionType(type, selectedIndex, BookSet, MovieSet);

    }

    public void callRecommendation() { // from books

        sortReco.setValue("Sort by: ");
        recommendation.getItems().clear();
        recommendation reco = new recommendation();
        Set<Book> recommendedBooks = reco.getBookRecommendation(portfolio.getSavedBookList()); // or getFavouriteBooks()
        Set<Movie> recommendedMovies = reco.getMovieRecommendation(portfolio.getSavedMovieList());

        RecoController recoList = new RecoController(recommendedBooks, recommendedMovies, recommendation, recos);
        recoList.display();

    }

    // Used to display what's in the book portfolio to the user
    // is called when a user updates (adds, removes, favourite's) a book
    @FXML
    protected void displayBooks() {

        bookList.clear();
        MLbookList.clear();
        MLfavBooks.clear();

        for (BookToPortfolio b : portfolio.getSavedBookList()) {

            MLbookList.add(b.getPbTitle());
            bookList.add(b.getPbTitle());

        }

        for (BookToPortfolio b : portfolio.getFavouriteBookList()) {

            MLfavBooks.add(b.getPbTitle());
            bookList.add("*" + b.getPbTitle());

        }

        sort.alphaSort(myBookList, bookList);
        ML_myBookList.setItems(MLbookList);
        favourite_books.setItems(MLfavBooks);

    }

    // Used to display what's in the movie portfolio to the user
    // is called when a user updates (adds, removes, favourite's) a movie
    @FXML
    protected void displayMovies() {

        movieList.clear();
        MLmovieList.clear();
        MLfavMovies.clear();

        for (MovieToPortfolio m : portfolio.getSavedMovieList()) {

            MLmovieList.add(m.getPmTitle());
            movieList.add(m.getPmTitle());

        }

        for (MovieToPortfolio m : portfolio.getFavouriteMovieList()) {

            MLfavMovies.add(m.getPmTitle());
            movieList.add("*" + m.getPmTitle());

        }

        sort.alphaSort(myMovieList, movieList);
        ML_myMovieList.setItems(MLmovieList);
        favourite_movies.setItems(MLfavMovies);

    }

    // Used to connect to the database and update the listViews for "my future list" in the "MyList" tab
    // it updates the list whenever a user adds/deletes movie/book to/from the list
    private void updateFutureList(ConnectionMethods method) {

        futureList = FXCollections.observableList(method.pullFutureList(validUserId));
        upNextList.setItems(futureList);

    }

    // Responsible for sending the content of book and movie objects to the database for the "My book/movie List"
    // saved objects and calling Book Movie functions to update the users list based on their actions on book/movie
    // object
    @FXML
    private void saveToMyCurrentList(int listViewIndex) {

        if (getType(listViewIndex).equals("Book")) {

            int i = 0;
            for (Book b : BookSet) {
                if (i == listViewIndex) {

                    BookToPortfolio book = new BookToPortfolio(b.getIsbn(), validUserId, b.getIdentifier(), b.getTitle(), b.getAuthor(), 0);
                    portfolio.updateBookPortfolio(book, "AddToSavedBooks");

                }
                i++;
            }

        } else if (getType(listViewIndex).equals("Movie")) {

            int i = 0;
            for (Movie m : MovieSet) {
                if (i == listViewIndex) {

                    MovieToPortfolio movie = new MovieToPortfolio(m.getId(), validUserId, m.getGenre(), m.getIdentifier(), m.getTitle(), m.getReleaseDate(), m.getOverview(), 0);
                    portfolio.updateMoviePortfolio(movie, "AddToSavedMovies");
                }
                i++;
            }

        } else {
            System.out.println("Error near line 557: No searchType value");
        }

    }

    // Responsible for sending the content of a book/movie object to the database table for "My Future List"
    // also updates the list the user sees by calling updateFutureList() method
    @FXML
    private void saveToMyFutureList(int listViewIndex) {

        ConnectionMethods method = new ConnectionMethods();

        if (getType(listViewIndex).equals("Book")) {

            int i = 0;
            for (Book b : BookSet) {
                if (i == listViewIndex) {

                    method.insertFutureList(b.getIsbn(), 0L, validUserId, b.getIdentifier(), b.getTitle(), b.getAuthor().toString(), null, null);
                }
                i++;
            }

        } else if (getType(listViewIndex).equals("Movie")) {

            int i = 0;
            for (Movie m : MovieSet) {
                if (i == listViewIndex) {

                    method.insertFutureList("null", m.getId(), validUserId, m.getIdentifier(), m.getTitle(), null, m.getReleaseDate(), m.getOverview());
                }
                i++;
            }

        } else {
            System.out.println("Error near line 593: No searchType value");
        }

        updateFutureList(method);
    }

    // Responsible for removing a book from the "My Book List" by removing the book from the database using its
    // unique id(isbn) then also calls the updateBooks() method to update the movies list seen by the user
    @FXML
    private void removeBook() {

        int selectedIndex = ML_myBookList.getSelectionModel().getSelectedIndex();

        if (selectedIndex == -1) {
            System.out.println("No Selected Item");
            return; // exit the method
        }

        int i = 0;
        for (BookToPortfolio b : portfolio.getSavedBookList()) {

            if (i == selectedIndex) {

                portfolio.updateBookPortfolio(b, "RemoveFromSavedBooks");
                return; // Once found exit the loop otherwise thread is Concurrently modifying the list

            }

            i++;
        }

    }

    // Responsible for adding a book to the favourites list by updating the is_favourite flag in the database
    // using method. calls, then also calls the updateBooks() method to update the books list seen by the user
    @FXML
    private void addBookToFavourites() {

        int selectedIndex = ML_myBookList.getSelectionModel().getSelectedIndex();

        if (selectedIndex == -1) {
            System.out.println("No Selected Item");
            return; // exit the method
        }

        int i = 0;
        for (BookToPortfolio b : portfolio.getSavedBookList()) {

            if (i == selectedIndex) {

                portfolio.updateBookPortfolio(b, "AddToFavouriteBooks");
                return; // Once found exit the loop otherwise thread is Concurrently modifying the list

            }

            i++;
        }

    }

    // Responsible for removing a book to the favourites list by updating the is_favourite flag in the database
    // using method. calls, then also calls the updateBooks() method to update the books list seen by the user
    @FXML
    private void removeBookFromFavourites() {

        int selectedIndex = favourite_books.getSelectionModel().getSelectedIndex();

        if (selectedIndex == -1) {
            System.out.println("No Selected Item");
            return; // exit the method
        }

        int i = 0;
        for (BookToPortfolio b : portfolio.getFavouriteBookList()) {

            if (i == selectedIndex) {

                portfolio.updateBookPortfolio(b, "RemoveFromFavouriteBooks");
                return; // Once found exit the loop otherwise thread is Concurrently modifying the list

            }

            i++;
        }

    }

    // Responsible for removing a movie from the "My Movie List" by removing the movie from the database using its
    // unique id then also calls the updateMovies() method to update the movies list seen by the user
    @FXML
    private void removeMovie() {

        int selectedIndex = ML_myMovieList.getSelectionModel().getSelectedIndex();

        if (selectedIndex == -1) {
            System.out.println("No Selected Item");
            return; // exit the method
        }

        int i = 0;
        for (MovieToPortfolio m : portfolio.getSavedMovieList()) {

            if (i == selectedIndex) {

                portfolio.updateMoviePortfolio(m, "RemoveFromSavedMovies");
                return; // Once found exit the loop otherwise thread is Concurrently modifying the list
            }

            i++;
        }
    }

    // Responsible for adding a movie to the favourites list by updating the is_favourite flag in the database
    // using method. calls, then also calls the updateMovies() method to update the movies list seen by the user
    @FXML
    private void addMovieToFavourites() {

        int selectedIndex = ML_myMovieList.getSelectionModel().getSelectedIndex();

        if (selectedIndex == -1) {
            System.out.println("No Selected Item");
            return; // exit the method
        }

        int i = 0;
        for (MovieToPortfolio m : portfolio.getSavedMovieList()) {

            if (i == selectedIndex) {

                System.out.println(m.getGenre());
                portfolio.updateMoviePortfolio(m, "AddToFavouriteMovies");
                return; // Once found exit the loop otherwise thread is Concurrently modifying the list
            }
            i++;

        }
    }

    // Responsible for removing a movie to the favourites list by updating the is_favourite flag in the database
    // using method. calls, then also calls the updateMovies() method to update the movies list seen by the user
    @FXML
    private void removeMovieFromFavourites() {

        int selectedIndex = favourite_movies.getSelectionModel().getSelectedIndex();

        if (selectedIndex == -1) {
            System.out.println("No Selected Item");
            return; // exit the method
        }

        int i = 0;
        for (MovieToPortfolio m : portfolio.getFavouriteMovieList()) {

            if (i == selectedIndex) {
                portfolio.updateMoviePortfolio(m, "RemoveFromFavouriteMovies");
                return; // Once found exit the loop otherwise thread is Concurrently modifying the list
            }

            i++;
        }
    }

    // Responsible for removing a movie/book from the "My Future Movie/Book List" by removing the movie/book
    // from the database using its unique id then also calls the updateFutureList() method to update the
    // movies/books list seen by the user
    @FXML
    private void removeFutureList() {
        //Need to change, will be problematic in the future
        ConnectionMethods method = new ConnectionMethods();

        String selectedItem = upNextList.getSelectionModel().getSelectedItem();
        final int selectedIndex = upNextList.getSelectionModel().getSelectedIndex();

        if (selectedIndex == -1) {
            System.out.println("No Selected Item");
            return; // exit the method
        }

        method.removeFutureList(selectedItem, validUserId);
        updateFutureList(method);
    }

    // Responsible for sorting the visible Book list alphabetically as well as the book portfolio
    // so that the references to unique Ids remain intact
    @FXML
    private void sortAlphaBook() {

        portfolio.movieSort();
        sort.alphaSort(ML_myBookList, MLbookList);
    }

    // Responsible for sorting the visible Movie list alphabetically as well as the movie portfolio
    // so that the references to unique Ids remain intact
    @FXML
    private void sortAlphaMovie() {
        portfolio.bookSort();
        sort.alphaSort(ML_myMovieList, MLmovieList);
    }

    // Responsible for calling sorting methods regarding the recommendation page
    @FXML
    private void recoSort(){

        if(sortReco.getValue().equals("Alphabetical")){
            sort.alphaSort(recommendation, recos);
        } else if(sortReco.getValue().equals("Books")){
            sort.bookSort(recommendation, recos);
        } else {
            sort.movieSort(recommendation, recos);
        }

    }

    // Responsible for handling user account creation
    @FXML
    private void createAccount() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/create_account.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/stylesheet.css")).toExternalForm());

        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.setTitle("Create an Account");
        newStage.setWidth(500);
        newStage.setHeight(500);
        newStage.show();

    }

    // Responsible for displaying, locking and unlocking tabs at login as well as updating all users list
    // on login
    public void login() {

        String username = usernameTxt.getText();
        String password = passwordTxt.getText();
        boolean successful = login.userLogin(username, password);

        if(successful){
            database.onLogin(validUserId);
            listen();
        }

    }

    // Responsible for locking, unlocking and displaying tabs when the user logs out
    public void logout() {

        logout = true;
        stage = (Stage) tabPane.getScene().getWindow();
        stage.setWidth(600);
        stage.setHeight(550);
        clearPane();
        listen();

        tabPane.getTabs().add(0, removedTabs.get(0));
        removedTabs.clear();

        initialize.setPane();
        LoginError.setText("");
    }

    // Responsible for logging the user out if they click the close button instead of logout button
    protected void listen() {

        stage = (Stage) tabPane.getScene().getWindow();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {

                if (!logout) {
                    clearPane();
                }

            }
        });
    }

    // Responsible for clearing everything after a user logs out
    @FXML
    private void clearPane() {

        database.sendToDatabase(validUserId);
        portfolio.clearMoviePortfolio();
        portfolio.clearBookPortfolio();
        sortReco.setValue("Sort by: ");
        searchBy.setValue("Search by");
        myListView.getItems().clear();
        searchType.setValue("Type");
        ErrorChecking.setText("");
        description.clear();
        passwordTxt.clear();
        usernameTxt.clear();
        searchText.clear();
        validUserId = "";
        clearSet();

    }

}