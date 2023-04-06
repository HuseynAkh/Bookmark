package home.yorku.bookmarks.controller;

import home.yorku.bookmarks.controller.database.ConnectionMethods;
import home.yorku.bookmarks.model.BookCheck;
import home.yorku.bookmarks.model.BookToPortfolio;
import home.yorku.bookmarks.model.MovieCheck;
import home.yorku.bookmarks.model.MovieToPortfolio;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class DatabaseController {

    private PortfolioController portfolio;
    private BookmarkController bookmark;

    private Set<BookCheck> booksToCheck;
    private Set<MovieCheck> moviesToCheck;
    public DatabaseController(BookmarkController bookmark, PortfolioController portfolio){

        this.bookmark = bookmark;
        this.portfolio = portfolio;

    }

    // Used to connect to the database and update the listViews for "my book list" in the "MyList" tab
    // it also updates the book portfolio whenever a user adds/deletes or moves a book to/from the favourite's tab
    // Used to connect to the database and update the listViews for "my movie list" in the "MyList" tab
    // it also updates the movie portfolio whenever a user adds/deletes or moves a movie to/from the favourite's tab
    public void onLogin(String user){

        ConnectionMethods method = new ConnectionMethods();
        method.userLogin(user, "Login");

        Set<BookToPortfolio> localBookSet = new HashSet<BookToPortfolio>();
        Set<MovieToPortfolio> localMovieSet = new HashSet<MovieToPortfolio>();

        portfolio.clearBookPortfolio();
        portfolio.clearMoviePortfolio();

        localBookSet = method.pullBooks(user);
        localMovieSet = method.pullMovies(user);

        for (BookToPortfolio b : localBookSet) {

            if (b.getPbIsFavourite() == 1) {
                portfolio.addToFavBookList(b);
            } else {
                portfolio.addToSavedBookList(b);
            }

        }

        for (MovieToPortfolio m : localMovieSet) {

            if (m.getPmIsFavourite() == 1) {
                portfolio.addToFavMovieList(m);
            } else {
                portfolio.addToSavedMovieList(m);
            }

        }

        bookmark.displayBooks();
        bookmark.displayMovies();
        method.closeConnection();
    }

    private void compareBooks(BookToPortfolio b, ConnectionMethods method) {

        String isbn = b.getPbIsbn();
        int favourite = 0;
        int exists = 0;

        for(BookCheck book: booksToCheck){
            if(isbn.equals(book.getIsbn())){
                favourite = book.getFavourite();
                exists++;
                break;
            }
        }

        if (exists > 0) {
            if (b.getPbIsFavourite() == 0 && favourite == 1) {
                method.removeFavouriteBook(b.getPbIsbn(), b.getPbUsername());
            } else if (b.getPbIsFavourite() == 1 && favourite == 0){
                method.addFavouriteBook(b.getPbIsbn(), b.getPbUsername());
            } // Else Do nothing

        } else {
            method.insertBook(b.getPbIsbn(), b.getPbUsername(), b.getPbIdentifier(), b.getPbTitle(), b.getPbAuthor().toString(), b.getPbIsFavourite());
        }

    }

    private void compareMovies(MovieToPortfolio m, ConnectionMethods method) {

        int exists = 0;
        int favourite = 0;
        Long movieId = m.getPmId();

        ArrayList<Long> genre = new ArrayList<>(m.getGenre());
        String genreString = String.join(",", genre.stream().map(String::valueOf).collect(Collectors.toList()));

        for(MovieCheck movie: moviesToCheck){
            if(movieId.equals(movie.getId())){
                favourite = movie.getFavourite();
                exists++;
                break;
            }
        }

        if (exists > 0) {

            if (m.getPmIsFavourite() == 0 && favourite == 1) {
                method.removeFavouriteMovie(m.getPmId(), m.getPmUsername());
            } else if (m.getPmIsFavourite() == 1 && favourite == 0){
                method.addFavouriteMovie(m.getPmId(), m.getPmUsername());
            } // Else Do nothing


        } else {
            method.insertMovie(m.getPmId(), m.getPmUsername(), genreString, m.getPmIdentifier(), m.getPmTitle(), m.getPmReleaseDate(), m.getPmDescription(), m.getPmIsFavourite());
        }

    }

    public void sendToDatabase(String user) {

        ConnectionMethods method = new ConnectionMethods();
        method.userLogin(user, "Logout");

        booksToCheck = method.pullBookIds(user);
        moviesToCheck = method.pullMovieIds(user);

        for (BookToPortfolio b : portfolio.getRemovedBooks()) {

            for (BookCheck book : booksToCheck) {

                String bookId = book.getIsbn();
                if (bookId.equals(b.getPbIsbn())) {
                    method.removeBook(b.getPbIsbn(), b.getPbUsername());
                }
            }

        }

        for (MovieToPortfolio m : portfolio.getRemovedMovies()) {

            for (MovieCheck movie : moviesToCheck) {

                Long movieId = movie.getId();
                if (movieId.equals(m.getPmId())) {
                    method.removeMovie(m.getPmId(), m.getPmUsername());
                }
            }

        }

        for (BookToPortfolio b : portfolio.getSavedBookList()) {
            compareBooks(b, method);
        }

        for (BookToPortfolio b : portfolio.getFavouriteBookList()) {
            compareBooks(b, method);
        }

        for (MovieToPortfolio m : portfolio.getSavedMovieList()) {
            compareMovies(m, method);
        }

        for (MovieToPortfolio m : portfolio.getFavouriteMovieList()) {
            compareMovies(m, method);
        }

        method.closeConnection();
    }

}
