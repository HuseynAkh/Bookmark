package home.yorku.bookmarks.controller;

import home.yorku.bookmarks.model.BookPortfolio;
import home.yorku.bookmarks.model.BookToPortfolio;
import home.yorku.bookmarks.model.MoviePortfolio;
import home.yorku.bookmarks.model.MovieToPortfolio;

import java.util.ArrayList;
import java.util.Comparator;

public class PortfolioController {
    private BookmarkController bookmark;
    private BookPortfolio bookPortfolio = new BookPortfolio();
    private MoviePortfolio moviePortfolio = new MoviePortfolio();
    private ArrayList<BookToPortfolio> removeBooks = new ArrayList<BookToPortfolio>();
    private ArrayList<MovieToPortfolio> removeMovies = new ArrayList<MovieToPortfolio>();

    public PortfolioController(BookmarkController bookmark){
        this.bookmark = bookmark;
    }
    protected void updateBookPortfolio(BookToPortfolio book, String update) {

        switch (update) {
            case "AddToSavedBooks": {

                if (this.bookPortfolio.getSavedBooks().stream().anyMatch(b -> b.getPbIsbn().equals(book.getPbIsbn()))) {
                    System.out.println("Book is already in your saved");
                } else {
                    this.bookPortfolio.AddToSavedBooks(book);
                    bookmark.displayBooks();
                }

                break;
            }
            case "RemoveFromSavedBooks": {

                removeBooks.add(book);
                this.bookPortfolio.RemoveFromSavedBooks(book);
                bookmark.displayBooks();

                break;
            }
            case "AddToFavouriteBooks": {

                book.setPbIsFavourite(1);
                this.bookPortfolio.AddToFavourites(book);
                this.bookPortfolio.RemoveFromSavedBooks(book);
                bookmark.displayBooks();

                break;
            }
            case "RemoveFromFavouriteBooks": {

                book.setPbIsFavourite(0);
                this.bookPortfolio.AddToSavedBooks(book);
                this.bookPortfolio.RemoveFromFavouriteBooks(book);
                bookmark.displayBooks();

                break;
            }
        }

    }

    protected void updateMoviePortfolio(MovieToPortfolio movie, String update) {

        switch (update) {
            case "AddToSavedMovies": {

                if (this.moviePortfolio.getSavedMovies().stream().anyMatch(m -> m.getPmId().equals(movie.getPmId()))) {
                    System.out.println("Movie is already in your saved");
                } else {
                    this.moviePortfolio.AddToSavedMovies(movie);
                    bookmark.displayMovies();
                }

                break;
            }
            case "RemoveFromSavedMovies": {

                removeMovies.add(movie);
                this.moviePortfolio.RemoveFromSavedMovies(movie);
                bookmark.displayMovies();

                break;
            }
            case "AddToFavouriteMovies": {

                movie.setPmIsFavourite(1);
                this.moviePortfolio.AddToFavourites(movie);
                this.moviePortfolio.RemoveFromSavedMovies(movie);
                bookmark.displayMovies();

                break;
            }
            case "RemoveFromFavouriteMovies": {

                movie.setPmIsFavourite(0);
                this.moviePortfolio.AddToSavedMovies(movie);
                this.moviePortfolio.RemoveFromFavouriteMovies(movie);
                bookmark.displayMovies();

                break;
            }
        }
    }

    protected void bookSort(){

        this.bookPortfolio.getSavedBooks().sort(new Comparator<BookToPortfolio>() {
            @Override
            public int compare(BookToPortfolio b1, BookToPortfolio b2) {
                return b1.getPbTitle().compareTo(b2.getPbTitle());
            }
        });

    }

    protected void movieSort(){

        this.moviePortfolio.getSavedMovies().sort(new Comparator<MovieToPortfolio>() {
            @Override
            public int compare(MovieToPortfolio m1, MovieToPortfolio m2) {
                return m1.getPmTitle().compareTo(m2.getPmTitle());
            }
        });

    }

    // Methods for BookmarkController Access
    protected ArrayList<BookToPortfolio> getSavedBookList(){
        return this.bookPortfolio.getSavedBooks();
    }
    protected ArrayList<BookToPortfolio> getFavouriteBookList(){
        return this.bookPortfolio.getFavouriteBooks();
    }
    protected ArrayList<MovieToPortfolio> getSavedMovieList(){
        return this.moviePortfolio.getSavedMovies();
    }
    protected ArrayList<MovieToPortfolio> getFavouriteMovieList(){
        return this.moviePortfolio.getFavouriteMovies();
    }

    // Methods for DataBaseController Access
    protected void clearBookPortfolio(){
        this.bookPortfolio.getSavedBooks().clear();
        this.bookPortfolio.getFavouriteBooks().clear();
    }
    protected void clearMoviePortfolio(){
        this.moviePortfolio.getSavedMovies().clear();
        this.moviePortfolio.getFavouriteMovies().clear();
    }
    protected void addToSavedBookList(BookToPortfolio book){
        this.bookPortfolio.AddToSavedBooks(book);
    }
    protected void addToFavBookList(BookToPortfolio book){
        this.bookPortfolio.AddToFavourites(book);
    }
    protected void addToSavedMovieList(MovieToPortfolio movie){
        this.moviePortfolio.AddToSavedMovies(movie);
    }
    protected void addToFavMovieList(MovieToPortfolio movie){
        this.moviePortfolio.AddToFavourites(movie);
    }
    protected ArrayList<BookToPortfolio> getRemovedBooks(){
        return this.removeBooks;
    }
    protected ArrayList<MovieToPortfolio> getRemovedMovies(){
        return this.removeMovies;
    }
}
