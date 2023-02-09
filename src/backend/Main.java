package backend;

import java.util.Set;

public class Main {
    public static void main (String [] args) {
        String search = "Tom Hanks";

        MovieSearch ms = new MovieSearch();
        Set<Movie> arr = ms.MovieByActor(search);

        for(Movie m : arr){
            System.out.println(m.getTitle());
        }
    }
}
