package home.yorku.bookmarks.model;

import java.util.ArrayList;

public class BookPortfolio {
    private ArrayList<Book> FavouriteBooks = new ArrayList<Book>(); //list of a users favourite books
    private ArrayList<Book> SavedBooks = new ArrayList<Book>(); //list of a users saved books

    public BookPortfolio (){

    }

    public void AddToFavourites(Book book){
        FavouriteBooks.add(book);
    }
    public void AddToSavedBooks (Book book){
        SavedBooks.add(book);
    }

    public void RemoveFromSavedBooks(Book book){
        SavedBooks.remove(book);
    }
    public ArrayList<Book> getFavouriteBooks(){
        return this.FavouriteBooks;
    }
    public ArrayList<Book> getSavedBooks(){
        return this.SavedBooks;
    }


}
