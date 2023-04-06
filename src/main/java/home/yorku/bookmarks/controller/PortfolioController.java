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
    public void updateBookPortfolio(BookToPortfolio book, String update) {

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

    public void updateMoviePortfolio(MovieToPortfolio movie, String update) {

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

    public void bookSort(){

        this.bookPortfolio.getSavedBooks().sort(new Comparator<BookToPortfolio>() {
            @Override
            public int compare(BookToPortfolio b1, BookToPortfolio b2) {
                return b1.getPbTitle().compareTo(b2.getPbTitle());
            }
        });

    }

    public void movieSort(){

        this.moviePortfolio.getSavedMovies().sort(new Comparator<MovieToPortfolio>() {
            @Override
            public int compare(MovieToPortfolio m1, MovieToPortfolio m2) {
                return m1.getPmTitle().compareTo(m2.getPmTitle());
            }
        });

    }

    // Methods for BookmarkController Access
    public ArrayList<BookToPortfolio> getSavedBookList(){
        return this.bookPortfolio.getSavedBooks();
    }
    public ArrayList<BookToPortfolio> getFavouriteBookList(){
        return this.bookPortfolio.getFavouriteBooks();
    }
    public ArrayList<MovieToPortfolio> getSavedMovieList(){
        return this.moviePortfolio.getSavedMovies();
    }
    public ArrayList<MovieToPortfolio> getFavouriteMovieList(){
        return this.moviePortfolio.getFavouriteMovies();
    }

    // Methods for DataBaseController Access
    public void clearBookPortfolio(){
        this.bookPortfolio.getSavedBooks().clear();
        this.bookPortfolio.getFavouriteBooks().clear();
    }
    public void clearMoviePortfolio(){
        this.moviePortfolio.getSavedMovies().clear();
        this.moviePortfolio.getFavouriteMovies().clear();
    }
    public void addToSavedBookList(BookToPortfolio book){
        this.bookPortfolio.AddToSavedBooks(book);
    }
    public void addToFavBookList(BookToPortfolio book){
        this.bookPortfolio.AddToFavourites(book);
    }
    public void addToSavedMovieList(MovieToPortfolio movie){
        this.moviePortfolio.AddToSavedMovies(movie);
    }
    public void addToFavMovieList(MovieToPortfolio movie){
        this.moviePortfolio.AddToFavourites(movie);
    }
    public ArrayList<BookToPortfolio> getRemovedBooks(){
        return this.removeBooks;
    }
    public ArrayList<MovieToPortfolio> getRemovedMovies(){
        return this.removeMovies;
    }
}
