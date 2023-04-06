package home.yorku.bookmarks.model;

public class MovieCheck {
    private Long id; // Movie id
    private int favourite; // used for filtering

    public MovieCheck(Long id, int favourite) { //constructor
        this.id = id;
        this.favourite = favourite;
    }

    public Long getId(){
        return this.id;
    }
    public int getFavourite(){
        return this.favourite;
    }
}
