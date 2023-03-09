package home.yorku.bookmarks.controller.sorting;

import home.yorku.bookmarks.model.Book;
import home.yorku.bookmarks.model.Movie;

import java.util.*;
import java.util.stream.Collectors;

public class AlphaSort {

    public AlphaSort(){

    }
    public Set<Movie> sortMovies(Set<Movie> list){
        List<Movie> sortedList = list.stream().sorted(Comparator.comparing(Movie::getTitle)).collect(Collectors.toList());

        list.clear();
        for(Movie m: sortedList) {
            list.add(m);
        }
        return list;
    }

    public Set<Book> sortBooks(Set<Book> list){
        List<Book> sortedList = list.stream().sorted(Comparator.comparing(Book::getTitle)).collect(Collectors.toList());

        list.clear();
        for(Book b: sortedList) {
            list.add(b);
        }
        return list;
    }
}
