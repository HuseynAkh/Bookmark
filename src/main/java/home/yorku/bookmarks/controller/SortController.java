package home.yorku.bookmarks.controller;

import home.yorku.bookmarks.controller.sorting.AlphaSort;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

import java.util.ArrayList;

public class SortController {

    private BookmarkController bookmark;
    public SortController(BookmarkController bookmark){
        this.bookmark = bookmark;
    }

    // Responsible actually sorting all the visible Book/Movie lists by converting the observable list to an
    // array list, sorting it using alphaSort. methods, the returning the sorted array as an observable array
    protected void alphaSort(ListView<String> list, ObservableList<String> items) {

        AlphaSort alphaSort = new AlphaSort();

        ArrayList<String> arrayList = new ArrayList<>(list.getItems());
        arrayList = alphaSort.sortMovies(arrayList);

        list.getItems().removeAll(items);
        list.getItems().addAll(arrayList);
    }

    protected void bookSort(ListView<String> list, ObservableList<String> items) {

        ArrayList<String> arrayList = new ArrayList<>(list.getItems());
        arrayList.sort((s1, s2) -> {
            if (s1.contains("|| Book") && !s2.contains("|| Book")) {
                return -1;
            } else if (!s1.contains("|| Book") && s2.contains("|| Book")) {
                return 1;
            } else {
                return s1.compareToIgnoreCase(s2);
            }
        });

        list.getItems().removeAll(items);
        list.getItems().addAll(arrayList);
    }

    protected void movieSort(ListView<String> list, ObservableList<String> items) {

        ArrayList<String> arrayList = new ArrayList<>(list.getItems());
        arrayList.sort((s1, s2) -> {
            if (s1.contains("|| Movie") && !s2.contains("|| Movie")) {
                return -1;
            } else if (!s1.contains("|| Movie") && s2.contains("|| Movie")) {
                return 1;
            } else {
                return s1.compareToIgnoreCase(s2);
            }
        });

        list.getItems().removeAll(items);
        list.getItems().addAll(arrayList);
    }
}
