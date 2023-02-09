import java.util.ArrayList;

public class MoviePortfolio {

    private ArrayList<Movie> FavouriteMovies = new ArrayList<Movie>();
    private ArrayList<Movie> SavedMovies = new ArrayList<Movie>();

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
    public ArrayList<Movie> getFavouriteMovies(){
        return this.FavouriteMovies;
    }
    public ArrayList<Movie> getSavedMovies(){
        return this.SavedMovies;
    }
}
