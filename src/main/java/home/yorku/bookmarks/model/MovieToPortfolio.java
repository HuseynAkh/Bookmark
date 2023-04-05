package home.yorku.bookmarks.model;

import java.util.ArrayList;

public class MovieToPortfolio {

    private Long movie_id; // unique id for movies
    private String username; // User_id in db
    private ArrayList<Long> genre;
    private String identifier = "Book"; //book identifier for database
    private String title; //title of book
    private String release_date; //list of authors for this book
    private String description;
    private int is_favourite; //used for filtering

    public MovieToPortfolio(Long movie_id, String username, ArrayList<Long> genre, String identifier, String title, String release_date, String description, int is_favourite) { //constructor
        this.movie_id = movie_id;
        this.username = username;
        this.genre = genre;
        this.identifier = identifier;
        this.title = title;
        this.release_date = release_date;
        this.description = description;
        this.is_favourite = is_favourite;
    }

    public Long getPmId(){
        return this.movie_id;
    }
    public String getPmUsername(){return this.username;}
    public ArrayList<Long> getGenre(){return this.genre;}
    public String getPmIdentifier(){return this.identifier;}
    public String getPmTitle(){
        return this.title;
    }
    public String getPmReleaseDate(){
        return this.release_date;
    }
    public String getPmDescription(){return this.description;}
    public int getPmIsFavourite(){
        return this.is_favourite;
    }
    public void setPmIsFavourite(int set){
        this.is_favourite = set;
    }
}
