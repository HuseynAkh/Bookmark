package home.yorku.bookmarks.controller.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConnectionMethods {
    //Change String to object Book or Movie later

    public void insertFutureList(int user_id, String identifier, String title, String isbn, String author, String release_date, String description) {

        try {
            DatabaseConnection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.query("Future_List");
            statement.setInt(1, user_id);
            statement.setString(2, identifier);
            statement.setString(3, title);
            statement.setString(4, isbn);
            statement.setString(5, author);
            statement.setString(6, release_date);
            statement.setString(7, description);
            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("A new book was inserted into FUTURE LIST successfully.");
            }

            connection.closeConnection();

        } catch (SQLException e) {
            System.out.println("Error inserting book: " + e.getMessage());
        }
    }
    public void insertBook(int user_id, String identifier, String title, String isbn,  String author) {

        try {
            DatabaseConnection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.query("Book");
            statement.setInt(1, user_id);
            statement.setString(2, identifier);
            statement.setString(3, title);
            statement.setString(4, isbn);
            statement.setString(5, author);
            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("A new book was inserted into BOOK LIST successfully.");
            }

            connection.closeConnection();

        } catch (SQLException e) {
            System.out.println("Error inserting book: " + e.getMessage());
        }
    }

    public void insertMovie(int user_id, String identifier, String title, String release_date, String description) {

        try {

            DatabaseConnection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.query("Movie");
            statement.setInt(1, user_id);
            statement.setString(2, identifier);
            statement.setString(3, title);
            statement.setString(4, release_date);
            statement.setString(5, description);
            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("A new movie was inserted into MOVIE LIST successfully.");
            }

            connection.closeConnection();

        } catch (SQLException e) {
            System.out.println("Error inserting movie: " + e.getMessage());
        }
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

    public ArrayList<String> pullBooks() {

        ArrayList<String> books = new ArrayList<>();

        try {

            DatabaseConnection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.query("Pull_Books");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String title = rs.getString("title");
                books.add(title);
            }


        } catch (SQLException e) {
            System.out.println("Error pulling Books: " + e.getMessage());

        }

        return books;
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

}
