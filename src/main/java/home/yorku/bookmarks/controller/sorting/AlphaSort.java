package home.yorku.bookmarks.controller.sorting;

import home.yorku.bookmarks.model.Book;
import home.yorku.bookmarks.model.Movie;

import java.util.*;
import java.util.stream.Collectors;

public class AlphaSort {

    public AlphaSort(){

    }
    public ArrayList<String> sortMovies(ArrayList<String> list){

        Collections.sort(list);
//        List<Movie> sortedList = list.stream().sorted(Comparator.comparing(Movie::getTitle)).collect(Collectors.toList());
//        list.clear();
//        for(Movie m: sortedList) {
//            list.add(m);
//        } // This chunk of code exists just incase it needs to be used in the future.
        return list;
    }

    public ArrayList<String> sortBooks(ArrayList<String> list){
        Collections.sort(list);
        return list;
    }
}
