package home.yorku.bookmarks.model;
public class BookCheck {
    private String isbn; // isbn for this book
    private int favourite; // used for filtering

    public BookCheck(String isbn, int favourite) { // constructor
        this.isbn = isbn;
        this.favourite = favourite;
    }

    public String getIsbn(){
        return this.isbn;
    }
    public int getFavourite(){
        return this.favourite;
    }

}
