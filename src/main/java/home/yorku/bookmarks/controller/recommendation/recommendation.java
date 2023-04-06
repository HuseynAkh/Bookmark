package home.yorku.bookmarks.controller.recommendation;

import home.yorku.bookmarks.controller.search.BookSearchManager;
import home.yorku.bookmarks.controller.search.MovieSearchManager;
import home.yorku.bookmarks.model.*;


import java.util.*;




public class recommendation {




    public Set<Book> getBookRecommendation (ArrayList<BookToPortfolio> booklist){
        Map<String, Integer> tally = new HashMap<String, Integer>();
        for(BookToPortfolio b : booklist){
            String author = b.getPbAuthor().get(0);
            if(tally.containsKey(author)){
                tally.replace(author, tally.get(author)+1);
            }
            else{
                tally.put(author, 1);
            }
        }
        List<Integer> count = new ArrayList<Integer>(tally.values());
        int maxVal = 0;
        for(int i : count){
            if(i>maxVal){
                maxVal = i;
            }
        }
        String result = getKey(tally, maxVal);
        BookSearchManager bm = new BookSearchManager();
        Set<Book> resultList = bm.searchBook(new SearchCriteria(
                BookmarkConstants.TYPE_BOOK,
                BookmarkConstants.KEY_BOOK_AUTHOR,
                result));

        return resultList;
    }




    public String getKey(Map<String,Integer> tally, int val){
        String result = "";
        if(tally.containsValue(val)){
            for(Map.Entry<String,Integer>entry : tally.entrySet()){
                if(Objects.equals(entry.getValue(),val)){
                    result = entry.getKey();
                }
            }
        }
        return result;
    }








    public Set<Movie> getMovieRecommendation (ArrayList<MovieToPortfolio> movieList){
        Map<Long, Integer> tally = new HashMap<Long, Integer>();
        for(MovieToPortfolio m : movieList) {
            ArrayList<Long> genres = m.getGenre();
            for (Long id : genres) {
                if (tally.containsKey(id)) {
                    tally.replace(id, tally.get(id) + 1);
                } else {
                    tally.put(id, 1);
                }
            }
        }




        List<Integer> count = new ArrayList<Integer>(tally.values());
        int maxVal = 0;




        for(int i : count){
            if(i > maxVal){
                maxVal = i;
            }
        }

        Long result = getMovieKey(tally, maxVal);
        MovieSearchManager mm = new MovieSearchManager();
        Set<Movie> resultList = mm.searchMovie(new SearchCriteria(
                BookmarkConstants.TYPE_MOVIE,
                BookmarkConstants.KEY_MOVIE_GENRE,
                (result.toString())));

        return resultList;
    }




    public Long getMovieKey(Map<Long, Integer> tally, int val){
        Long result = 0L;
        if(tally.containsValue(val)){
            for(Map.Entry<Long, Integer> entry : tally.entrySet()){
                if(Objects.equals(entry.getValue(), val)){
                    result = entry.getKey();
                }
            }
        }




        return result;
    }




}


