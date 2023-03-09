package home.yorku.bookmarks.controller.search;

import home.yorku.bookmarks.model.Book;
import home.yorku.bookmarks.model.BookmarkConstants;
import home.yorku.bookmarks.model.SearchCriteria;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

//Strategy design pattern: Context and Client implementation
public class BookSearchManager {

    SearchStrategyIF searchStrategy = null;

    public Set searchBook (SearchCriteria searchCriteria){
        Set<Book> books = new HashSet<Book>();
        URL url = null;

        if(searchCriteria.getSearchKey().equals(BookmarkConstants.KEY_BOOK_NAME)){
            this.searchStrategy = new BookNameSearchStrategy();
        }else if(searchCriteria.getSearchKey().equals(BookmarkConstants.KEY_BOOK_AUTHOR)){
            this.searchStrategy = new BookAuthorSearchStrategy();
        }else if(searchCriteria.getSearchKey().equals(BookmarkConstants.KEY_BOOK_GENRE)){
            this.searchStrategy = new BookGenreSearchStrategy();
        }

        //using the strategy
        searchBooksAPI(books, searchStrategy.getSearchURL(searchCriteria));

        return books;
    }

    private static void searchBooksAPI(Set<Book> books, URL url) {
        try {
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int responseCode = con.getResponseCode();

            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {
                StringBuilder informationString = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    informationString.append((scanner.nextLine()));
                }
                scanner.close();

                extractBookInfo(books, informationString);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void extractBookInfo(Set<Book> books, StringBuilder informationString) throws ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(String.valueOf(informationString));
        JSONArray array = new JSONArray();
        array.add(obj);

        JSONObject bookData = (JSONObject) array.get(0);
        JSONArray arr = (JSONArray) bookData.get("docs");
        for (Object o : arr) {
            JSONObject book = (JSONObject) o;
            String title = (String) book.get("title_suggest");
            JSONArray authorArr = (JSONArray) book.get("author_name");
            JSONArray isbnArr = (JSONArray) book.get("edition_key");

            String isbn = (String)isbnArr.get(0);

            ArrayList<String> authors = new ArrayList<String>();
            if(authorArr == null){
                authors.add("Unknown");
            }
            else {
                for (Object author : authorArr) {
                    authors.add((String) author);
                }
            }

            Book b = new Book(title, authors, isbn, "");
            books.add(b);
        }
    }

    public String GetBookDescription (String id) {
        String bookDescription = "";
        try {
            URL url = new URL("https://openlibrary.org/"+id+".json");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int responseCode = con.getResponseCode();

            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {
                StringBuilder informationString = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    informationString.append((scanner.nextLine()));
                }
                scanner.close();

                JSONParser parser = new JSONParser();
                Object obj = parser.parse(String.valueOf(informationString));
                JSONArray array = new JSONArray();
                array.add(obj);
                JSONObject data = (JSONObject) array.get(0);

                bookDescription = (String) data.get("description");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookDescription;
    }


}
