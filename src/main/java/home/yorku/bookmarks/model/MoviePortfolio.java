package home.yorku.bookmarks.model;

import java.util.ArrayList;

public class MoviePortfolio {

    private ArrayList<Movie> FavouriteMovies = new ArrayList<Movie>(); //list of a users favourite movies
    private ArrayList<Movie> SavedMovies = new ArrayList<Movie>(); //list of a users saved movies

    public MoviePortfolio(){

    }

    public void AddToFavourites(Movie movie){
        FavouriteMovies.add(movie);
    }
    public void AddToSavedMovies(Movie movie){
        SavedMovies.add(movie);
    }
    public void RemoveFromSavedMovies(Movie movie){
        SavedMovies.remove(movie);
    }

    public void RemoveFromFavouriteMovies(Movie movie) {FavouriteMovies.remove(movie);}
    public ArrayList<Movie> getFavouriteMovies(){
        return this.FavouriteMovies;
    }
    public ArrayList<Movie> getSavedMovies(){
        return this.SavedMovies;
    }
}
