package home.yorku.bookmarks.model;

import java.util.ArrayList;

public class BookToPortfolio {

    private String isbn; //isbn (Openlibrary Key) for this book
    private String username;
    private String identifier = "Book"; //book identifier for database
    private String title; //title of book
    private ArrayList<String> author; //list of authors for this book
    private int is_favourite; //used for filtering

    public BookToPortfolio(String isbn, String username, String identifier, String title, ArrayList<String> author, int is_favourite) { //constructor
        this.isbn = isbn;
        this.username = username;
        this.identifier = identifier;
        this.title = title;
        this.author = author;
        this.is_favourite = is_favourite;
    }

    public String getPbIsbn(){
        return this.isbn;
    }
    public String getPbUsername(){return this.username;}
    public String getPbIdentifier(){return this.identifier;}
    public String getPbTitle(){
        return this.title;
    }
    public ArrayList<String> getPbAuthor(){
        return this.author;
    }
    public int getPbIsFavourite(){
        return this.is_favourite;
    }
    public void setPbIsFavourite(int set){
        this.is_favourite = set;
    }
}

