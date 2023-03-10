package home.yorku.bookmarks.controller.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConnectionMethods {
    //Change String to object Book or Movie later
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
                System.out.println("A new book was inserted successfully.");
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
                System.out.println("A new movie was inserted successfully.");
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
                System.out.println(title);
                movies.add(title);
            }

            connection.closeConnection();

        } catch (SQLException e) {
            System.out.println("Error inserting movie: " + e.getMessage());

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
                System.out.println(title);
                books.add(title);
            }

            connection.closeConnection();

        } catch (SQLException e) {
            System.out.println("Error inserting movie: " + e.getMessage());

        }

        return books;
    }


    //For backend db to work we need to give a unique id to each book and movie object on add
    //or add the entire object to the database and just pull from the "titles" in the db
    /*
    public void removeBook(int bookId) {
        try {

            String sql = "DELETE FROM my_book_list WHERE book_ID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, bookId);
            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("The book with ID " + bookId + " was deleted successfully.");
            } else {
                System.out.println("No books were deleted.");
            }

        } catch (SQLException e) {
            System.out.println("Error deleting book: " + e.getMessage());
        }
    }

     */




}
