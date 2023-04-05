package home.yorku.bookmarks.model;

import java.util.ArrayList;

public class Movie {
    private String title; //movie title

    private String identifier = "Movie"; //identifier for database
    private String overview; //overview of this movie
    private String releaseDate; //release date of this movie
    private Long id;
    private int is_favourite;

    private ArrayList<Long> genre;

    public Movie(Long id, String title, ArrayList<Long> genre, String releaseDate, String overview, int is_favourite) { //constructor
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.overview = overview;
        this.is_favourite = is_favourite;
    }

    public Long getId() {
        return id;
    }

    public String getTitle(){
        return this.title;
    }
    public String getOverview(){
        return this.overview;
    }

    public String getReleaseDate(){return this.releaseDate;}

    public String getIdentifier(){return this.identifier;}

    public ArrayList<Long> getGenre(){return this.genre;}

    public int getIsFavourite(){return this.is_favourite;}

}
