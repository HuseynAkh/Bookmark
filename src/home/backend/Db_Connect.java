package home.backend;
import java.sql.*;

public class Db_Connect {

    private Connection connection = null;
    public void connect() {
        try {
            String url = "jdbc:mariadb://sql958.main-hosting.eu/u880453721_Bookmark";
            String user = "u880453721_user";
            String password = "@BookmarkDbcp01";
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to database.");
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        }
    }

    public void disconnect() {
        try {
            connection.close();
            System.out.println("Disconnected from database.");
        } catch (SQLException e) {
            System.out.println("Error disconnecting from database: " + e.getMessage());
        }
    }
    //Change String to object Book or Movie later
    public void insertBook(int user_id, String title, String author, String genre, String description) {

        try {
            connect();
            String sql = "INSERT INTO my_book_list (user_id, title, author, genre, description) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, user_id);
            statement.setString(2, title);
            statement.setString(3, author);
            statement.setString(4, genre);
            statement.setString(5, description);
            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("A new book was inserted successfully.");
            }

            disconnect();
        } catch (SQLException e) {
            System.out.println("Error inserting book: " + e.getMessage());
        }
    }

    public void insertMovie(int user_id, String title, String release_date, String description) {

        try {
            connect();
            String sql = "INSERT INTO my_movie_list (user_ID, title, release_date, description) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, user_id);
            statement.setString(2, title);
            statement.setString(3, release_date);
            statement.setString(4, description);
            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("A new movie was inserted successfully.");
            }

            disconnect();
        } catch (SQLException e) {
            System.out.println("Error inserting movie: " + e.getMessage());
        }
    }

    //For backend db to work we need to give a unique id to each book and movie object on add
    //or add the entire object to the database and just pull from the "titles" in the db
    public void removeBook(int bookId) {
        try {
            connect();
            String sql = "DELETE FROM my_book_list WHERE book_ID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, bookId);
            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("The book with ID " + bookId + " was deleted successfully.");
            } else {
                System.out.println("No books were deleted.");
            }

            disconnect();
        } catch (SQLException e) {
            System.out.println("Error deleting book: " + e.getMessage());
        }
    }
}
