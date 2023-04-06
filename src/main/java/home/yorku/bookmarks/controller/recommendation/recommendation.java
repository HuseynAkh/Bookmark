package home.yorku.bookmarks.controller.recommendation;

import home.yorku.bookmarks.model.BookToPortfolio;
import home.yorku.bookmarks.model.MovieToPortfolio;


import java.util.*;




public class recommendation {




    public void getBookRecommendation (ArrayList<BookToPortfolio> booklist){
        Map<String, Integer> tally = new HashMap<String, Integer>();
        for(BookToPortfolio b : booklist){
            String author = b.getPbAuthor().get(0);
            System.out.println(author);
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
        System.out.println(result);




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








    public void getMovieRecommendation (ArrayList<MovieToPortfolio> movieList){
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
        System.out.println(result);
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


