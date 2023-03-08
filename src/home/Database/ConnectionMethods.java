package home.Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConnectionMethods {
    //Change String to object Book or Movie later
    public void insertBook(int user_id, String title, String author, String genre, String description) {

        try {
            DatabaseConnection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.query("Book");
            statement.setInt(1, user_id);
            statement.setString(2, title);
            statement.setString(3, author);
            statement.setString(4, genre);
            statement.setString(5, description);
            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("A new book was inserted successfully.");
            }

        } catch (SQLException e) {
            System.out.println("Error inserting book: " + e.getMessage());
        }
    }

    public void insertMovie(int user_id, String title, String release_date, String description) {

        try {

            DatabaseConnection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.query("Movie");
            statement.setInt(1, user_id);
            statement.setString(2, title);
            statement.setString(3, release_date);
            statement.setString(4, description);
            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("A new movie was inserted successfully.");
            }


        } catch (SQLException e) {
            System.out.println("Error inserting movie: " + e.getMessage());
        }
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
