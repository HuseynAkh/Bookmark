package backend;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class BookSearch {

    public BookSearch () {
    }

    public void searchBookName (String search) {
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

                Set<Book> bookSet = new HashSet<Book>();

                for (Object o : arr) {
                    JSONObject book = (JSONObject) o;
                    String title = (String) book.get("title_suggest");
                    String author = (String) book.get("author");
                    bookSet.add(new Book(title, author,""));
                }
                for (Book book : bookSet) {
                    System.out.println(book.getTitle());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void searchBookAuthor (String search) {
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

                Set<Book> bookSet = new HashSet<Book>();

                for (Object o : arr) {
                    JSONObject book = (JSONObject) o;
                    String title = (String) book.get("title_suggest");
                    String author = (String) book.get("author");
                    bookSet.add(new Book(title, author, ""));
                }
                for (Book book : bookSet) {
                    System.out.println(book.getTitle());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SearchBookGenre(String search){
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
                        String author = (String) book.get("author");
                        bookSet.add(new Book(title, author,""));
                    }
                    for (Book book : bookSet) {
                        System.out.println(book.getTitle());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
