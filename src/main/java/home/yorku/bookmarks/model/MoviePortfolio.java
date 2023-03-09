package home.yorku.bookmarks.model;

import java.util.ArrayList;
//This class is responsible for adding, removing, and retrieving a users movie portfolio
public class MoviePortfolio {

    private ArrayList<Movie> FavouriteMovies = new ArrayList<Movie>(); //list of a users favourite movies
    private ArrayList<Movie> SavedMovies = new ArrayList<Movie>(); //list of a users saved movies

    public MoviePortfolio(){

    }
    //This method adds a favourited movie to the favourites list
    public void AddToFavourites(Movie movie){
        FavouriteMovies.add(movie);
    }
    //This method adds movies to the saved movies list
    public void AddToSavedMovies(Movie movie){
        SavedMovies.add(movie);
    }
    //This method removes movies from the saved movies list
    public void RemoveFromSavedMovies(Movie movie){
        SavedMovies.remove(movie);
    }
    //This method removes favourited movies from the favourite list
    public void RemoveFromFavouriteMovies(Movie movie) {FavouriteMovies.remove(movie);}
    //This method retrieves the list of favourited movies
    public ArrayList<Movie> getFavouriteMovies(){
        return this.FavouriteMovies;
    }
    //This method retrieves the list of movies a user has saved
    public ArrayList<Movie> getSavedMovies(){
        return this.SavedMovies;
    }
}
