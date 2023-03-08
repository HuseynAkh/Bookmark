package home.backend;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Book {
    private String title; //title of book

    private String identifier = "Book"; //book identifier for database
    private ArrayList<String> author; //list of authors for this book

    private String isbn; //isbn (Openlibrary Key) for this book
    private String description; //description for this book (NO LONGER IN USE)

    public Book(String title, ArrayList<String> author, String isbn, String description) { //constructor
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.description = description;
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
    public String getDescription(){
        return this.description;
    }

    public String getIdentifier(){return this.identifier;}
}
