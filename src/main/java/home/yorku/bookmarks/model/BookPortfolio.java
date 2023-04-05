package home.yorku.bookmarks.model;

import java.util.ArrayList;

public class BookPortfolio {
    private ArrayList<BookToPortfolio> FavouriteBooks = new ArrayList<BookToPortfolio>(); //list of a users favourite books
    private ArrayList<BookToPortfolio> SavedBooks = new ArrayList<BookToPortfolio>(); //list of a users saved books

    public BookPortfolio (){

    }

    public void AddToFavourites(BookToPortfolio book){
        FavouriteBooks.add(book);
    }
    public void AddToSavedBooks (BookToPortfolio book){
        SavedBooks.add(book);
    }
    public void RemoveFromSavedBooks(BookToPortfolio book){
        SavedBooks.remove(book);
    }
    public void RemoveFromFavouriteBooks (BookToPortfolio book) { FavouriteBooks.remove(book); } // Removes book object from favourite list
    public ArrayList<BookToPortfolio> getFavouriteBooks(){
        return this.FavouriteBooks;
    }
    public ArrayList<BookToPortfolio> getSavedBooks(){
        return this.SavedBooks;
    }


}
