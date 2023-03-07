package home.backend;

public class Movie {
    private String title;

    private String identifier = "Movie";
    private String overview;
    private String releaseDate;

    public Movie(String title, String releaseDate, String overview) {
        this.title = title;
        this.overview = overview;
        this.releaseDate = releaseDate;
    }
    public String getTitle(){
        return this.title;
    }
    public String getOverview(){
        return this.overview;
    }

    public String getReleaseDate(){return this.releaseDate;}

    public String getIdentifier(){return this.identifier;}

}
