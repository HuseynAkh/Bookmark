package home.backend;

public class Movie {
    private String title;
    private String overview;

    public Movie(String title, String overview) {
        this.title = title;
        this.overview = overview;
    }
    public String getTitle(){
        return this.title;
    }
    public String getOverview(){
        return this.overview;
    }

}
