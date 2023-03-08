package home.backend;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


public class BookSearch {

    private Set<Book> books = new HashSet<Book>();
    private ImageDownloader imgDL;
    private CoverUrlExtractor cue;

    public BookSearch () {
        this.imgDL = new ImageDownloader();
        this.cue = new CoverUrlExtractor();
    }

    public Set searchBookName (String search) {
        this.books.clear();
        search = search.replaceAll(" ", "+");
        try {
            URL url = new URL("https://openlibrary.org/search.json?q=" + search);
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
                    this.books.add(b);


                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.books;
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


    public Set searchBookAuthor (String search) {
        this.books.clear();
        search = search.replaceAll(" ", "%20");
        try {
            URL url = new URL("https://openlibrary.org/search.json?author=" + search + "&sort=new");
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
                JSONObject bookData = (JSONObject) array.get(0);

                JSONArray arr = (JSONArray) bookData.get("docs");


                for (Object o : arr) {
                    JSONObject book = (JSONObject) o;
                    String title = (String) book.get("title_suggest");
                    JSONArray authorArr = (JSONArray) book.get("author_name");

                    ArrayList<String> authors = new ArrayList<String>();
                    if(authorArr == null){
                        authors.add("Unknown");
                    }
                    else {
                        for (Object author : authorArr) {
                            authors.add((String) author);
                        }
                    }
                    String id = (String)book.get("key");
                    String BookDescription = GetBookDescription(id);
                    if(BookDescription == null){
                        BookDescription = "There is no description for this book yet.";
                    }


                    this.books.add(new Book(title, authors,"", BookDescription));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.books;
    }

    public Set SearchBookGenre(String search){
        this.books.clear();
        search = search.replaceAll(" ", "%20");
        try {
            URL url = new URL("https://openlibrary.org/search.json?subject=" + search + "&sort=new");
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
                JSONObject bookData = (JSONObject) array.get(0);

                JSONArray arr = (JSONArray) bookData.get("docs");

                Set<Book> bookSet = new HashSet<Book>();

                for (Object o : arr) {
                    JSONObject book = (JSONObject) o;
                    String title = (String) book.get("title_suggest");
                    JSONArray authorArr = (JSONArray) book.get("author_name");

                    ArrayList<String> authors = new ArrayList<String>();
                    if(authorArr == null){
                        authors.add("Unknown");
                    }
                    else {
                        for (Object author : authorArr) {
                            authors.add((String) author);
                        }
                    }
                    String id = (String)book.get("key");
                    String BookDescription = GetBookDescription(id);
                    if(BookDescription == null){
                        BookDescription = "There is no description for this book yet.";
                    }


                    this.books.add(new Book(title, authors,"", BookDescription));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.books;
    }
}