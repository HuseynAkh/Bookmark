package home.yorku.bookmarks.model;

import java.util.ArrayList;
//This class is responsible for adding, removing, and retrieving a users movie portfolio
public class MoviePortfolio {

    private ArrayList<MovieToPortfolio> FavouriteMovies = new ArrayList<MovieToPortfolio>(); //list of a users favourite movies
    private ArrayList<MovieToPortfolio> SavedMovies = new ArrayList<MovieToPortfolio>(); //list of a users saved movies

    public MoviePortfolio(){

    }
    //This method adds a favourite movie to the favourites list
    public void AddToFavourites(MovieToPortfolio movie){
        FavouriteMovies.add(movie);
    }
    //This method adds movies to the saved movies list
    public void AddToSavedMovies(MovieToPortfolio movie){
        SavedMovies.add(movie);
    }
    //This method removes movies from the saved movies list
    public void RemoveFromSavedMovies(MovieToPortfolio movie){
        SavedMovies.remove(movie);
    }
    //This method removes favourite movies from the favourite list
    public void RemoveFromFavouriteMovies(MovieToPortfolio movie) {FavouriteMovies.remove(movie);}
    //This method retrieves the list of favourite movies
    public ArrayList<MovieToPortfolio> getFavouriteMovies(){
        return this.FavouriteMovies;
    }
    //This method retrieves the list of movies a user has saved
    public ArrayList<MovieToPortfolio> getSavedMovies(){
        return this.SavedMovies;
    }
}
