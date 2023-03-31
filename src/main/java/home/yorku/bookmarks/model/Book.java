package home.yorku.bookmarks.model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Book {
    private String title; //title of book

    private String identifier = "Book"; //book identifier for database
    private ArrayList<String> author; //list of authors for this book

    private String isbn; //isbn (Openlibrary Key) for this book
    private int is_favourite; //used for filtering

    public Book(String title, ArrayList<String> author, String isbn, int is_favourite) { //constructor
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.is_favourite = is_favourite;
    }
    public String getTitle(){
        return this.title;
    }
    public ArrayList<String> getAuthor(){
        return this.author;
    }
    public String getIsbn(){
        return this.isbn;
    }
    public int getIsFavourite(){
        return this.is_favourite;
    }
    public String getIdentifier(){return this.identifier;}
}
