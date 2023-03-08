package home.backend;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Book {
    private String title;

    private String identifier = "Book";
    private ArrayList<String> author;

    private String isbn;
    private String description;

    public Book(String title, ArrayList<String> author, String isbn, String description) {
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
