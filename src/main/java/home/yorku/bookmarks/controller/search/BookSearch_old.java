package home.Search;

import home.backend.Book;
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

    private Set<Book> books = new HashSet<Book>(); //Set to store search results
    private ImageDownloader imgDL; //ImageDownloader object to retrieve book covers
    private CoverUrlExtractor cue; //CoverUrlExtractor object to retrieve url to book cover image file


    public BookSearch () { //constructor
        this.imgDL = new ImageDownloader();
        this.cue = new CoverUrlExtractor();
    }

    public Set searchBookName (String search) { // method to search by book name
        this.books.clear(); //clear previous result set
        search = search.replaceAll(" ", "+"); //format user input appropriately for url
        try {
            URL url = new URL("https://openlibrary.org/search.json?q=" + search); //this URL is specific to search book by name

            //connect and search url
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int responseCode = con.getResponseCode();

            // JSON retrieval (this chunk of code will be removed, it is now in JsonInfoParser class
            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {
                StringBuilder informationString = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    informationString.append((scanner.nextLine())); //grab json info
                }
                scanner.close();

                JSONParser parser = new JSONParser();
                Object obj = parser.parse(String.valueOf(informationString)); // parse json to string
                JSONArray array = new JSONArray();
                array.add(obj); // add to array. this is the only way that is working for us. Basically this is a JSONArray with only 1 element. this element is the entire JSON text
                JSONObject bookData = (JSONObject) array.get(0); // covert JSON array element into JSONObject

                JSONArray arr = (JSONArray) bookData.get("docs"); //get JSON array tagged "docs" in JSON text

                // for each book in the result JSON, retrieve and store its title, authors, and book cover image
                for (Object o : arr) {
                    JSONObject book = (JSONObject) o;
                    String title = (String) book.get("title_suggest"); //grab title element in Json text
                    JSONArray authorArr = (JSONArray) book.get("author_name"); //grab author name in Json text
                    JSONArray isbnArr = (JSONArray) book.get("edition_key"); //grap OpenLibrary edition key for this book in Json text. We are using OL key since there are more books with OL keys than isbns. this key is used to get the book cover image

                    String isbn = (String)isbnArr.get(0); //get first OL edition key for book. JSON array tagged "edition_key" in JSON text is an array of Keys. we will use the first key.

                    //get list of authors of this book
                    ArrayList<String> authors = new ArrayList<String>();
                    if(authorArr == null){ //some books dont have authors, these books will have an Unknown author as its author.
                        authors.add("Unknown");
                    }
                    else {
                        for (Object author : authorArr) { //for each author in the authors array in JSON text
                            authors.add((String) author); //add each author as String into arrayList of authors.
                        }
                    }

                    Book b = new Book(title, authors, isbn, ""); //create new book object with our retrieved information.
                    this.books.add(b); //store in result set.


                }
            }
        } catch (Exception e) { //exception handler
            e.printStackTrace();
        }
        return this.books;
    }
    public String GetBookDescription (String id) { //get the decription of a book (THIS METHOD IS NO LONGER IN USE AND WILL BE DELETED. IT WILL NOT BE COMMENTED)
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


    public Set searchBookAuthor (String search) { //search books written by a specirfic author
        this.books.clear(); //clear previous result set
        search = search.replaceAll(" ", "%20"); //format user input string for Url
        try {
            URL url = new URL("https://openlibrary.org/search.json?author=" + search + "&sort=new"); //this url is specific to search book by author name

            //open and send HTTP url request.
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
                    informationString.append((scanner.nextLine())); //grab JSON result text
                }
                scanner.close();

                JSONParser parser = new JSONParser();
                Object obj = parser.parse(String.valueOf(informationString)); //parse JSON result text in to String
                JSONArray array = new JSONArray();
                array.add(obj); // Add into JSONArray. this implementation is the only way it works for us.
                JSONObject bookData = (JSONObject) array.get(0); //Store first element (which is the entire JSON text) as a JSON Object.

                JSONArray arr = (JSONArray) bookData.get("docs"); //retrieve JSON array tagged "docs" in JSON result text

                //Retrieve and store title, author name, and book cover picture for each book in JSON text.
                for (Object o : arr) {
                    JSONObject book = (JSONObject) o;
                    String title = (String) book.get("title_suggest"); //grab title
                    JSONArray authorArr = (JSONArray) book.get("author_name"); // grab author name

                    //get array of authors who wrote the book
                    ArrayList<String> authors = new ArrayList<String>();
                    if(authorArr == null){
                        authors.add("Unknown"); // some books do not have authors, these books will have Unknown as their author.
                    }
                    else {
                        for (Object author : authorArr) { //for each author in the author array for a specific book
                            authors.add((String) author); //store author in String Arraylist
                        }
                    }
                    //this part until the end of the class is outdated code. qwill be updated soon
                    String id = (String)book.get("key"); //get the OpenLibrary edition key
                    String BookDescription = GetBookDescription(id); // grab the description of the book (NO LONGER IN USE)
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

    public Set SearchBookGenre(String search){ //search book by genre (OUTDATED)
        this.books.clear(); //clear existing result ste
        search = search.replaceAll(" ", "%20"); //format user input String for url
        try {
            URL url = new URL("https://openlibrary.org/search.json?subject=" + search + "&sort=new"); // this URL is specific only to the search book by author
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
                    informationString.append((scanner.nextLine())); //grab JSON result information
                }
                scanner.close();

                JSONParser parser = new JSONParser();
                Object obj = parser.parse(String.valueOf(informationString)); //parse JSON result text into String
                JSONArray array = new JSONArray();
                array.add(obj); //add entire JSON String result into 1st element of array
                JSONObject bookData = (JSONObject) array.get(0); // convert first element of array in to JSON obejct

                JSONArray arr = (JSONArray) bookData.get("docs"); //get JSON array in JSON text tagged "docs"

                Set<Book> bookSet = new HashSet<Book>();

                for (Object o : arr) { //for each book in the result array
                    JSONObject book = (JSONObject) o;
                    String title = (String) book.get("title_suggest"); //grab title
                    JSONArray authorArr = (JSONArray) book.get("author_name"); //grab author name

                    ArrayList<String> authors = new ArrayList<String>();
                    if(authorArr == null){
                        authors.add("Unknown"); //if author is null, show Unknown as author name
                    }
                    else {
                        for (Object author : authorArr) { //for each author in author array for a specific book
                            authors.add((String) author); //add author as String to author array
                        }
                    }
                    //THIS PART UNTIL THE END IS NO LONGER IN USE
                    String id = (String)book.get("key"); //grab edition key of book
                    String BookDescription = GetBookDescription(id); //grab description of the book (NO LONGER IN USE)
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