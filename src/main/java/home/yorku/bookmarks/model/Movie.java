package home.yorku.bookmarks.model;

public class Movie {
    private String title; //movie title

    private String identifier = "Movie"; //identifier for database
    private String overview; //overview of this movie
    private String releaseDate; //release date of this movie
    private Long id;
    private int is_favourite;

    public Movie(Long id, String title, String releaseDate, String overview, int is_favourite) { //constructor
        this.id = id;
        this.title = title;
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

    public int getIsFavourite(){return this.is_favourite;}

}
