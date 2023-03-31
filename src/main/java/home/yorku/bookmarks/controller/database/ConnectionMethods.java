package home.yorku.bookmarks.controller.database;

import home.yorku.bookmarks.model.Book;
import home.yorku.bookmarks.model.Movie;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ConnectionMethods {
    //Change String to object Book or Movie later

    public void userLogin(String user_id, String type) {

        try {
            DatabaseConnection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.query("User");
            statement.setString(1, user_id);
            statement.setString(2, type);
            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0 && type.equals("Login")) {
                System.out.println("A user has logged-in");
            }

            if (rowsInserted > 0 && type.equals("Logout")) {
                System.out.println("A user has logged-out");
            }

            connection.closeConnection();

        } catch (SQLException e) {
            System.out.println("Error in login/logout: " + e.getMessage());
        }
    }

    public void insertBook(String book_id, String user_id, String identifier, String title, String author, int is_favourite) {

        try {
            DatabaseConnection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.query("Book");
            statement.setString(1, book_id);
            statement.setString(2, user_id);
            statement.setString(3, identifier);
            statement.setString(4, title);
            statement.setString(5, author);
            statement.setInt(6, is_favourite);
            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("A new book was inserted into BOOK LIST successfully.");
            }

            connection.closeConnection();

        } catch (SQLException e) {
            System.out.println("Error inserting book: " + e.getMessage());
        }
    }

    public void insertMovie(String movie_id, String user_id, String identifier, String title, String release_date, String movie_dsc, int is_favourite) {

        try {

            DatabaseConnection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.query("Movie");
            statement.setString(1, movie_id);
            statement.setString(2, user_id);
            statement.setString(3, identifier);
            statement.setString(4, title);
            statement.setString(5, release_date);
            statement.setString(6, movie_dsc);
            statement.setInt(7, is_favourite);

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("A new movie was inserted into MOVIE LIST successfully.");
            }

            connection.closeConnection();

        } catch (SQLException e) {
            System.out.println("Error inserting movie: " + e.getMessage());
        }
    }

    public void insertFutureList(String book_id, String movie_id, String user_id, String identifier, String title, String author, String release_date, String movie_dsc) {

        try {
            DatabaseConnection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.query("Future_List");
            statement.setString(1, book_id);
            statement.setString(2, movie_id);
            statement.setString(3, user_id);
            statement.setString(4, identifier);
            statement.setString(5, title);
            statement.setString(6, author);
            statement.setString(7, release_date);
            statement.setString(8, movie_dsc);
            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("A new book was inserted into FUTURE LIST successfully.");
            }

            connection.closeConnection();

        } catch (SQLException e) {
            System.out.println("Error inserting book: " + e.getMessage());
        }
    }

    public Set<Book> pullBooks() {

        //ArrayList<String> books = new ArrayList<>();
        Set<Book> books = new HashSet<Book>();

        try {

            DatabaseConnection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.query("Pull_Books");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                ArrayList<String> authorToList = new ArrayList<>();
                String isbn = rs.getString("book_id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                authorToList.add(author);

                Book book = new Book(title, authorToList, isbn, "" );
                books.add(book);
            }


        } catch (SQLException e) {
            System.out.println("Error pulling Books: " + e.getMessage());

        }

        return books;
    }

    public ArrayList<String> pullMovies() {

        ArrayList<String> movies = new ArrayList<>();

        try {

            DatabaseConnection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.query("Pull_Movies");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String title = rs.getString("title");
                movies.add(title);
            }

        } catch (SQLException e) {
            System.out.println("Error pulling Movies: " + e.getMessage());

        }

        return movies;
    }

    public ArrayList<String> pullFutureList() {

        ArrayList<String> futureList = new ArrayList<>();

        try {

            DatabaseConnection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.query("Pull_Future_List");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String title = rs.getString("title");
                futureList.add(title);
            }


        } catch (SQLException e) {
            System.out.println("Error inserting into FUTURE LIST: " + e.getMessage());

        }

        return futureList;
    }



    public void closeConnection(){

        try {

            DatabaseConnection connection = DatabaseConnection.getInstance();
            connection.closeConnection();

        } catch (SQLException e) {
            System.out.println("Error disconnecting: " + e.getMessage());

        }

    }

    public void removeFutureList(String title){
        try {

            DatabaseConnection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.query("Delete_Future_List");
            statement.setString(1, title);
            int rowsDeleted = statement.executeUpdate();

            System.out.println(rowsDeleted + " row deleted.");
            connection.closeConnection();

        } catch (SQLException e) {
            System.out.println("Error removing item from MY FUTURE LIST: " + e.getMessage());

        }
    }


    //For future backend db to work better need to give a unique id to each book and movie object on add
    //or add the entire object to the database and just pull from the "titles" in the db

    public void removeMovie(String title) {

        try {

            DatabaseConnection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.query("Delete_Movie");
            statement.setString(1, title);
            int rowsDeleted = statement.executeUpdate();

            System.out.println(rowsDeleted + " row deleted.");
            connection.closeConnection();

        } catch (SQLException e) {
            System.out.println("Error removing movie: " + e.getMessage());

        }
    }

    public void removeBook(String title) {

        try {

            DatabaseConnection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.query("Delete_Book");
            statement.setString(1, title);
            int rowsDeleted = statement.executeUpdate();

            System.out.println(rowsDeleted + " row deleted.");
            connection.closeConnection();

        } catch (SQLException e) {
            System.out.println("Error removing book: " + e.getMessage());

        }
    }

    public void addFavourite(String title) {

        try {

            DatabaseConnection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.query("Add_Favourite");
            statement.setString(1, title);
            statement.executeUpdate();

            System.out.println("Favourite added");
            connection.closeConnection();

        } catch (SQLException e) {
            System.out.println("Error adding to favourites: " + e.getMessage());

        }
    }

    public void removeFavourite(String title) {

        try {

            DatabaseConnection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.query("Remove_Favourite");
            statement.setString(1, title);
            statement.executeUpdate();

            System.out.println("Favourite removed");
            connection.closeConnection();

        } catch (SQLException e) {
            System.out.println("Error removing from favourites: " + e.getMessage());

        }
    }

}
