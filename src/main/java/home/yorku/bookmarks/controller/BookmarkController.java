package home.yorku.bookmarks.controller;

import home.yorku.bookmarks.controller.database.ConnectionMethods;
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
    private final ObservableList<String> moviesSearchOptions = FXCollections.observableArrayList(
            "Title", "Actor"
    );
    private final ObservableList<String> booksSearchOptions = FXCollections.observableArrayList(
            "Title", "Genre", "Author"
    );
    List<Tab> removedTabs = new ArrayList<>();
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TabPane tabPane;
    @FXML
    private VBox LoginBox;
    @FXML
    private VBox RecoBox;
    private Stage stage;
    @FXML
    private ChoiceBox<String> searchType;
    @FXML
    private ChoiceBox<String> searchBy;
    @FXML
    private TextField usernameTxt;
    @FXML
    private PasswordField passwordTxt;
    private String validUserId = "";
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
    private final ObservableList<String> bookList = FXCollections.observableArrayList();
    @FXML
    private ListView<String> myMovieList;
    private final ObservableList<String> movieList = FXCollections.observableArrayList();
    @FXML
    private ListView<String> ML_myBookList;
    private final ObservableList<String> MLbookList = FXCollections.observableArrayList();
    @FXML
    private ListView<String> favourite_books;
    private final ObservableList<String> MLfavBooks = FXCollections.observableArrayList();
    @FXML
    private ListView<String> ML_myMovieList;
    private final ObservableList<String> MLmovieList = FXCollections.observableArrayList();
    @FXML
    private ListView<String> favourite_movies;
    private final ObservableList<String> MLfavMovies = FXCollections.observableArrayList();
    @FXML
    private ListView<String> upNextList;
    private ObservableList<String> futureList = FXCollections.observableArrayList();
    @FXML
    private ImageView coverImageView;
    @FXML
    private Label titleLabel;
    @FXML
    private Label idLabel;
    @FXML
    private Label authorOrRelease;
    @FXML
    private Label descLabel;
    @FXML
    private Label description;
    private Set<Book> BookSet;
    private Set<Movie> MovieSet;
    private final ArrayList<BookToPortfolio> removeBooks = new ArrayList<BookToPortfolio>();
    private ArrayList<MovieToPortfolio> removeMovies = new ArrayList<MovieToPortfolio>();
    private BookPortfolio bookPortfolio;
    private MoviePortfolio moviePortfolio;
    private double sceneHeight;
    private double sceneWidth;
    private boolean logout = false;
    private String myList = "";
    private String upNext = "";

    //private Tab Book;

    public BookmarkController() {
    }

    // Initializes all listviews, dynamic buttons, tab views on login,
    // book/movie portfolio's for each user session to manipulate during
    // run time
    @FXML
    private void initialize() {
        // Initialize portfolios
        bookPortfolio = new BookPortfolio();
        moviePortfolio = new MoviePortfolio();
        // Initialize the list when null
        myBookList.setItems(bookList);
        upNextList.setItems(futureList);
        myMovieList.setItems(movieList);
        ML_myBookList.setItems(MLbookList);
        ML_myMovieList.setItems(MLmovieList);
        favourite_books.setItems(MLfavBooks);
        favourite_movies.setItems(MLfavMovies);

        Scene scene = anchorPane.getScene();
        if (scene != null) {
            sceneHeight = scene.getHeight();
            sceneWidth = scene.getWidth();
        }

        // All dynamic login box layouts
        anchorPane.sceneProperty().addListener((observable, oldScene, newScene) -> {

            if (newScene != null) {
                newScene.heightProperty().addListener((obs, oldHeight, newHeight) -> {
                    sceneHeight = newHeight.doubleValue();
                    LoginBox.setLayoutY((sceneHeight / 2) - (20 + LoginBox.getHeight() / 2)); // 20 for padding between boxes
                    RecoBox.setLayoutY((sceneHeight / 2) - (20 + RecoBox.getHeight() / 2));

                });

                newScene.widthProperty().addListener((obs, oldWidth, newWidth) -> {
                    sceneWidth = newWidth.doubleValue();
                    LoginBox.setLayoutX((sceneWidth / 2) - (LoginBox.getWidth() / 2));
                    RecoBox.setLayoutX((sceneWidth / 2) - (RecoBox.getWidth() / 2));

                });
            }

        });

        // Initialize locked Panes
        setPane();

        // Initialize search options for Books and Movies
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

    // function to disable tabPanes on login/logout
    private void setPane() {
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
        clearDescription();
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
                    MovieSet = search.searchMovie(searchCriteria);
                    MovieController movieController = new MovieController(BookSet, MovieSet, myListView);
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

            myList = "Books I've read";
            upNext = "Books I want to read";

        } else {
            ErrorChecking.setTextFill(Color.RED);
            ErrorChecking.setText("Please choose a selection from the drop down title \"Type\" and \"Search by\" ");
        }

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

    // Updates the description view box with the description of the movie
    // If the selection is a book it will load the cover image of the book
    @FXML
    private void callDescription() throws IOException {

        final int selectedIndex = myListView.getSelectionModel().getSelectedIndex();

        clearDescription();

        if (getType(selectedIndex).equals("Movie")) {

            int i = 0;
            for (Movie m : MovieSet) {
                if (i == selectedIndex) {
                    descLabel.setText("Description: ");
                    descLabel.setPadding(new Insets(1, 1, 5, 5));
                    setDescription(m.getTitle(), m.getIdentifier(), "Release date: ", m.getReleaseDate());
                    description.setText(m.getOverview());
                    description.setPadding(new Insets(5, 5, 5, 5));
                }
                i++;
            }
        }

        if (getType(selectedIndex).equals("Book")) {
            CoverUrlExtractor url = new CoverUrlExtractor();

            int i = 0;
            for (Book b : BookSet) {
                if (i == selectedIndex) {
                    url.getBookCover(b.getIsbn());
                    setDescription(b.getTitle(), b.getIdentifier(), "Author(s): ", b.getAuthor().toString());

                    if (url.getBookCover(b.getIsbn())) {
                        InputStream stream = Files.newInputStream(Paths.get("./temporary.jpg"));
                        Image coverImage = new Image(stream);
                        coverImageView.setImage(coverImage);
                        coverImageView.setFitWidth(100);
                        coverImageView.setFitHeight(200);
                    } else {
                        InputStream stream = Files.newInputStream(Paths.get("images/book-placeholder.jpg"));
                        Image coverImage = new Image(stream);
                        coverImageView.setImage(coverImage);
                        coverImageView.setFitWidth(100);
                        coverImageView.setFitHeight(200);

                    }
                }
                i++;
            }
        }

        System.out.println("clicked on " + myListView.getSelectionModel().getSelectedItem());
    }

    // Responsible for setting, updating and formatting text labels and descriptions for movies and books
    private void setDescription(String title, String identifier, String dynamicLabel, String dynamicText) {

        titleLabel.setText("Title: " + title);
        titleLabel.setPadding(new Insets(1, 1, 5, 5));
        idLabel.setText("Type: " + identifier);
        idLabel.setPadding(new Insets(1, 1, 5, 5));

        if (identifier.equals("Book")) {
            String author = dynamicText.substring(1, dynamicText.length() - 1);
            authorOrRelease.setText(dynamicLabel + author);
        } else {
            authorOrRelease.setText(dynamicLabel + ": " + dynamicText);
        }
        authorOrRelease.setPadding(new Insets(1, 1, 5, 5));

    }

    private void clearDescription() {

        titleLabel.setText("");
        idLabel.setText("");
        authorOrRelease.setText("");
        descLabel.setText("");
        description.setText("");
        coverImageView.setImage(null);

    }

    // Used to connect to the database and update the listViews for "my book list" in the "MyList" tab
    // it also updates the book portfolio whenever a user adds/deletes or moves a book to/from the favourite's tab
    private void updateBookList(ConnectionMethods method) {

        Set<BookToPortfolio> localBookSet = new HashSet<BookToPortfolio>();

        this.bookPortfolio.getSavedBooks().clear();
        this.bookPortfolio.getFavouriteBooks().clear();

        localBookSet = method.pullBooks(validUserId);

        for (BookToPortfolio b : localBookSet) {

            if (b.getPbIsFavourite() == 1) {
                this.bookPortfolio.AddToFavourites(b);
            } else {
                this.bookPortfolio.AddToSavedBooks(b);
            }

        }

        displayBooks();
    }

    // Used to connect to the database and update the listViews for "my movie list" in the "MyList" tab
    // it also updates the movie portfolio whenever a user adds/deletes or moves a movie to/from the favourite's tab
    private void updateMovieList(ConnectionMethods method) {

        Set<MovieToPortfolio> localMovieSet = new HashSet<MovieToPortfolio>();

        this.moviePortfolio.getSavedMovies().clear();
        this.moviePortfolio.getFavouriteMovies().clear();

        localMovieSet = method.pullMovies(validUserId);

        for (MovieToPortfolio m : localMovieSet) {

            if (m.getPmIsFavourite() == 1) {
                this.moviePortfolio.AddToFavourites(m);
            } else {
                this.moviePortfolio.AddToSavedMovies(m);

            }

        }

        displayMovies();
    }

    // Used to connect to the database and update the listViews for "my future list" in the "MyList" tab
    // it updates the list whenever a user adds/deletes movie/book to/from the list
    private void updateFutureList(ConnectionMethods method) {

        futureList = FXCollections.observableList(method.pullFutureList(validUserId));
        upNextList.setItems(futureList);

    }

    @FXML
    private void displayBooks() {

        bookList.clear();
        MLbookList.clear();
        MLfavBooks.clear();

        for (BookToPortfolio b : this.bookPortfolio.getSavedBooks()) {

            MLbookList.add(b.getPbTitle());
            bookList.add(b.getPbTitle());

        }

        for (BookToPortfolio b : this.bookPortfolio.getFavouriteBooks()) {

            MLfavBooks.add(b.getPbTitle());
            bookList.add("*" + b.getPbTitle());

        }

        alphaSort(myBookList, bookList);
        ML_myBookList.setItems(MLbookList);
        favourite_books.setItems(MLfavBooks);

    }

    @FXML
    private void displayMovies() {

        movieList.clear();
        MLmovieList.clear();
        MLfavMovies.clear();

        for (MovieToPortfolio m : this.moviePortfolio.getSavedMovies()) {

            MLmovieList.add(m.getPmTitle());
            movieList.add(m.getPmTitle());

        }

        for (MovieToPortfolio m : this.moviePortfolio.getFavouriteMovies()) {

            MLfavMovies.add(m.getPmTitle());
            movieList.add("*" + m.getPmTitle());

        }

        alphaSort(myMovieList, movieList);
        ML_myMovieList.setItems(MLmovieList);
        favourite_movies.setItems(MLfavMovies);

    }

    private void compareBooks(ArrayList<String> bookIds, BookToPortfolio b, ConnectionMethods method) {

        String isbn = b.getPbIsbn();

        if (bookIds.contains(isbn)) {

            if (b.getPbIsFavourite() == 0) {
                method.removeFavouriteBook(b.getPbIsbn(), b.getPbUsername());
            } else {
                method.addFavouriteBook(b.getPbIsbn(), b.getPbUsername());
            }

            return;
        }

        method.insertBook(b.getPbIsbn(), b.getPbUsername(), b.getPbIdentifier(), b.getPbTitle(), b.getPbAuthor().toString(), b.getPbIsFavourite());

    }

    private void compareMovies(ArrayList<Long> movieIds, MovieToPortfolio m, ConnectionMethods method) {

        Long id = m.getPmId();
        ArrayList<Long> genre = new ArrayList<>(m.getGenre());
        String genreString =  String.join(",", genre.stream().map(String::valueOf).collect(Collectors.toList()));

        if (movieIds.contains(id)) {

            if (m.getPmIsFavourite() == 0) {
                method.removeFavouriteMovie(m.getPmId(), m.getPmUsername());
            } else {
                method.addFavouriteMovie(m.getPmId(), m.getPmUsername());
            }

            return;
        }

        method.insertMovie(m.getPmId(), m.getPmUsername(), genreString, m.getPmIdentifier(), m.getPmTitle(), m.getPmReleaseDate(), m.getPmDescription(), m.getPmIsFavourite());

    }

    private void sendToDatabase(ConnectionMethods method) {

        ArrayList<String> bookIds = method.pullBookIds(validUserId);
        ArrayList<Long> movieIds = method.pullMovieIds(validUserId);

        for (BookToPortfolio b : removeBooks) {

            for (String id : bookIds) {
                if (id.equals(b.getPbIsbn())) {
                    method.removeBook(b.getPbIsbn(), b.getPbUsername());
                }
            }

        }

        for (MovieToPortfolio m : removeMovies) {

            for (Long id : movieIds) {
                if (id.equals(m.getPmId())) {
                    method.removeMovie(m.getPmId(), m.getPmUsername());
                }
            }

        }

        for (BookToPortfolio b : this.bookPortfolio.getSavedBooks()) {
            compareBooks(bookIds, b, method);
        }

        for (BookToPortfolio b : this.bookPortfolio.getFavouriteBooks()) {
            compareBooks(bookIds, b, method);
        }

        for (MovieToPortfolio m : this.moviePortfolio.getSavedMovies()) {
            compareMovies(movieIds, m, method);
        }

        for (MovieToPortfolio m : this.moviePortfolio.getFavouriteMovies()) {
            compareMovies(movieIds, m, method);
        }

        method.closeConnection();
    }

    private void updateBookPortfolio(BookToPortfolio book, String update) {

        switch (update) {
            case "AddToSavedBooks": {

                if (this.bookPortfolio.getSavedBooks().stream().anyMatch(b -> b.getPbIsbn().equals(book.getPbIsbn()))) {
                    System.out.println("Book is already in your saved");
                } else {
                    this.bookPortfolio.AddToSavedBooks(book);
                    displayBooks();
                }

                break;
            }
            case "RemoveFromSavedBooks": {

                removeBooks.add(book);
                this.bookPortfolio.RemoveFromSavedBooks(book);
                displayBooks();

                break;
            }
            case "AddToFavouriteBooks": {

                book.setPbIsFavourite(1);
                this.bookPortfolio.AddToFavourites(book);
                this.bookPortfolio.RemoveFromSavedBooks(book);
                displayBooks();

                break;
            }
            case "RemoveFromFavouriteBooks": {

                book.setPbIsFavourite(0);
                this.bookPortfolio.AddToSavedBooks(book);
                this.bookPortfolio.RemoveFromFavouriteBooks(book);
                displayBooks();

                break;
            }
        }

    }

    private void updateMoviePortfolio(MovieToPortfolio movie, String update) {

        switch (update) {
            case "AddToSavedMovies": {

                if (this.moviePortfolio.getSavedMovies().stream().anyMatch(m -> m.getPmId().equals(movie.getPmId()))) {
                    System.out.println("Movie is already in your saved");
                } else {
                    this.moviePortfolio.AddToSavedMovies(movie);
                    displayMovies();
                }

                break;
            }
            case "RemoveFromSavedMovies": {

                removeMovies.add(movie);
                this.moviePortfolio.RemoveFromSavedMovies(movie);
                displayMovies();

                break;
            }
            case "AddToFavouriteMovies": {

                movie.setPmIsFavourite(1);
                this.moviePortfolio.AddToFavourites(movie);
                this.moviePortfolio.RemoveFromSavedMovies(movie);
                displayMovies();

                break;
            }
            case "RemoveFromFavouriteMovies": {

                movie.setPmIsFavourite(0);
                this.moviePortfolio.AddToSavedMovies(movie);
                this.moviePortfolio.RemoveFromFavouriteMovies(movie);
                displayMovies();

                break;
            }
        }

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
                    updateBookPortfolio(book, "AddToSavedBooks");

                }
                i++;
            }

        } else if (getType(listViewIndex).equals("Movie")) {

            int i = 0;
            for (Movie m : MovieSet) {
                if (i == listViewIndex) {

                    MovieToPortfolio movie = new MovieToPortfolio(m.getId(), validUserId, m.getGenre(), m.getIdentifier(), m.getTitle(), m.getReleaseDate(), m.getOverview(), 0);
                    updateMoviePortfolio(movie, "AddToSavedMovies");
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
        for (BookToPortfolio b : this.bookPortfolio.getSavedBooks()) {

            if (i == selectedIndex) {

                updateBookPortfolio(b, "AddToFavouriteBooks");
                return; // Once found exit the loop otherwise java.util.ConcurrentModificationException

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
        for (BookToPortfolio b : this.bookPortfolio.getFavouriteBooks()) {

            if (i == selectedIndex) {

                updateBookPortfolio(b, "RemoveFromFavouriteBooks");
                return; // Once found exit the loop otherwise java.util.ConcurrentModificationException

            }

            i++;
        }

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
        for (BookToPortfolio b : this.bookPortfolio.getSavedBooks()) {

            if (i == selectedIndex) {

                updateBookPortfolio(b, "RemoveFromSavedBooks");
                return; // Once found exit the loop otherwise java.util.ConcurrentModificationException

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
        for (MovieToPortfolio m : this.moviePortfolio.getSavedMovies()) {

            if (i == selectedIndex) {

                System.out.println(m.getGenre());
                updateMoviePortfolio(m, "AddToFavouriteMovies");
                return; // Once found exit the loop otherwise java.util.ConcurrentModificationException
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
        for (MovieToPortfolio m : this.moviePortfolio.getFavouriteMovies()) {

            if (i == selectedIndex) {
                updateMoviePortfolio(m, "RemoveFromFavouriteMovies");
                return; // Once found exit the loop otherwise java.util.ConcurrentModificationException
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
        for (MovieToPortfolio m : this.moviePortfolio.getSavedMovies()) {

            if (i == selectedIndex) {

                updateMoviePortfolio(m, "RemoveFromSavedMovies");
                return; // Once found exit the loop otherwise java.util.ConcurrentModificationException
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

        this.bookPortfolio.getSavedBooks().sort(new Comparator<BookToPortfolio>() {
            @Override
            public int compare(BookToPortfolio b1, BookToPortfolio b2) {
                return b1.getPbTitle().compareTo(b2.getPbTitle());
            }
        });

        alphaSort(ML_myBookList, MLbookList);
    }


    // Responsible for sorting the visible Movie list alphabetically as well as the movie portfolio
    // so that the references to unique Ids remain intact
    @FXML
    private void sortAlphaMovie() {
        this.moviePortfolio.getSavedMovies().sort(new Comparator<MovieToPortfolio>() {
            @Override
            public int compare(MovieToPortfolio m1, MovieToPortfolio m2) {
                return m1.getPmTitle().compareTo(m2.getPmTitle());
            }

        });

        alphaSort(ML_myMovieList, MLmovieList);
    }

    // Responsible actually sorting all the visible Book/Movie lists by converting the observable list to an
    // array list, sorting it using alphaSort. methods, the returning the sorted array as an observable array
    private void alphaSort(ListView<String> list, ObservableList<String> items) {

        AlphaSort alphaSort = new AlphaSort();

        ArrayList<String> arrayList = new ArrayList<>(list.getItems());
        arrayList = alphaSort.sortMovies(arrayList);

        list.getItems().removeAll(items);
        list.getItems().addAll(arrayList);
    }

    private void onLogin() {

        ConnectionMethods method = new ConnectionMethods();

        method.userLogin(validUserId, "Login");
        updateBookList(method);
        updateMovieList(method);
        updateFutureList(method);

        method.closeConnection();

    }

    public void setLoginPane(){

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

    // Responsible for displaying, locking and unlocking tabs at login as well as updating all users list
    // on login
    public void login() {

        ConnectionMethods method = new ConnectionMethods();
        String username = usernameTxt.getText();
        String password = passwordTxt.getText();

        if (!username.equals("") && !password.equals("")) {

            int verify = method.checkCrd(username, password);

            if (verify == 1) {

                validUserId = username;
                logout = false;
                stage = (Stage) tabPane.getScene().getWindow();
                stage.setWidth(900);
                stage.setHeight(680);
                onLogin();
                listen();

                setLoginPane();

            } else {
                // Later feature add for get password or prompt user to creat account
                LoginError.setTextFill(Color.RED);
                LoginError.setText("Invalid user name or password");

            }

        } else if (usernameTxt.getText().isEmpty() && !passwordTxt.getText().isEmpty()) {

            LoginError.setTextFill(Color.RED);
            LoginError.setText("Please enter a username to Login");

        } else if (!usernameTxt.getText().isEmpty() && passwordTxt.getText().isEmpty()) {

            LoginError.setTextFill(Color.RED);
            LoginError.setText("Please enter a password to Login");

        } else {
            LoginError.setTextFill(Color.RED);
            LoginError.setText("Please enter a username & password to Login");
        }
    }

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

    @FXML
    private void clearPane() {

        ConnectionMethods method = new ConnectionMethods();
        method.userLogin(validUserId, "Logout");

        clearSet();
        sendToDatabase(method);
        clearDescription();
        validUserId = "";
        searchText.clear();
        removeBooks.clear();
        usernameTxt.clear();
        passwordTxt.clear();
        ErrorChecking.setText("");
        searchType.setValue("Type");
        myListView.getItems().clear();
        searchBy.setValue("Search by");
        this.bookPortfolio.getSavedBooks().clear();
        this.bookPortfolio.getFavouriteBooks().clear();
        this.moviePortfolio.getSavedMovies().clear();
        this.moviePortfolio.getFavouriteMovies().clear();

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

        setPane();
        LoginError.setText("");
    }

    // Responsible for logging the user out if they click the close button instead of logout button
    public void listen() {

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
    public void callRecommendation(){ //fro books
        recommendation reco = new recommendation();
        reco.getBookRecommendation(this.bookPortfolio.getSavedBooks()); // or getFavouriteBooks()
        reco.getMovieRecommendation(this.moviePortfolio.getSavedMovies());


    }

}