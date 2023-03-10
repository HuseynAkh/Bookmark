package home.yorku.bookmarks.controller.database;
import java.sql.*;

public class DatabaseConnection {

    private static DatabaseConnection instance;
    private Connection connection;
    private String url = "jdbc:mariadb://sql958.main-hosting.eu/u880453721_Bookmark";
    private String user = "u880453721_user";
    private String password = "@BookmarkDbcp01";

    private DatabaseConnection() throws SQLException{
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            this.connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to database.");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        }
    }

    public Connection getConnection(){
        return connection;
    }

    public static DatabaseConnection getInstance() throws SQLException {
        if (instance == null) {
            instance = new DatabaseConnection();
        } else if (instance.getConnection().isClosed()) {
            instance = new DatabaseConnection();
        }

        return instance;
    }

    public void closeConnection(){
        try {
            connection.close();
            System.out.println("Database connection closed");
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }
    public PreparedStatement query(String type) throws SQLException {

        String sql = "null";

        if(type.equals("Book")){
            sql = "INSERT INTO my_book_list (user_id, identifier, title, isbn, author) VALUES (?, ?, ?, ?, ?)";
        }else if(type.equals("Movie")) {
            sql = "INSERT INTO my_movie_list (user_ID, identifier, title, release_date, description) VALUES (?, ?, ?, ?, ?)";
        }else if(type.equals("Future_List")) {
            sql = "INSERT INTO my_future_list (user_ID, identifier, title, isbn, author, release_date, description) VALUES (?, ?, ?, ?, ?, ?, ?)";
        }else if(type.equals("Delete_Movie")){
            sql = "DELETE FROM my_movie_list WHERE title = ?";
        }else if(type.equals("Delete_Book")){
            sql = "DELETE FROM my_book_list WHERE title = ?";
        }else if(type.equals("Delete_Future_List")){
            sql = "DELETE FROM my_future_list WHERE title = ?";
        }else if(type.equals("Pull_Movies")){
            sql = "Select title FROM my_movie_list";
        }else if(type.equals("Pull_Books")){
            sql = "Select title FROM my_book_list";
        }else if(type.equals("Pull_Future_List")) {
            sql = "Select title FROM my_future_list";
        }

        return connection.prepareStatement(sql);
    }
}
