package home.yorku.bookmarks.controller.database;
import java.sql.*;

public class DatabaseConnection {

    private static DatabaseConnection instance;
    private Connection connection;
    private String url = "jdbc:mariadb://sql958.main-hosting.eu/u880453721_Bookmark";
    private String user = "u880453721_user";
    private String password = "@BookmarkDbcp01";


    private DatabaseConnection() {
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

        switch(type){
            case "User":{
                sql = "INSERT INTO user_log (user_id, type_log, time_stamp) VALUES (?, ?, CURRENT_TIMESTAMP)";
                break;
            }
            case "Book":{
                sql = "INSERT INTO my_book_list (book_id, user_id, identifier, title, author, is_favourite, time_stamp) VALUES (?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";
                break;
            }
            case "Movie":{
                sql = "INSERT INTO my_movie_list (movie_id, user_id, identifier, title, release_date, movie_dsc, is_favourite, time_stamp) VALUES (?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";
                break;
            }
            case "Future_List":{
                sql = "INSERT INTO my_future_list (book_id, movie_id, user_ID, identifier, title, author, release_date, movie_dsc, time_stamp) VALUES (?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";
                break;
            }
            case "Delete_Movie":{
                sql = "DELETE FROM my_movie_list WHERE movie_id = ? AND user_id = ?";
                break;
            }
            case "Delete_Book":{
                sql = "DELETE FROM my_book_list WHERE book_id = ? AND user_id = ?";
                break;
            }
            case "Delete_Future_List":{
                sql = "DELETE FROM my_future_list WHERE title = ? AND user_id = ?";
                break;
            }
            case "Pull_Books":{
                sql = "SELECT book_id, title, author, is_favourite FROM my_book_list WHERE user_id = ? ORDER BY time_stamp";
                break;
            }
            case "Pull_Movies":{
                sql = "SELECT movie_id, title, release_date, movie_dsc, is_favourite FROM my_movie_list WHERE user_id = ? ORDER BY time_stamp";
                break;
            }
            case "Pull_Future_List":{
                sql = "SELECT title FROM my_future_list WHERE user_id = ? ORDER BY time_stamp";
                break;
            }
            case "Add_FavouriteBook":{
                sql = "UPDATE my_book_list SET is_favourite = 1 WHERE book_id = ? AND user_id = ?";
                break;
            }
            case "Remove_FavouriteBook":{
                sql = "UPDATE my_book_list SET is_favourite = 0 WHERE book_id = ? AND user_id = ?";
                break;
            }
            case "Add_FavouriteMovie":{
                sql = "UPDATE my_movie_list SET is_favourite = 1 WHERE movie_id = ? AND user_id = ?";
                break;
            }
            case "Remove_FavouriteMovie":{
                sql = "UPDATE my_movie_list SET is_favourite = 0 WHERE movie_id = ? AND user_id = ?";
                break;
            }
            default:
                System.out.println("Query type does not exist");
        }

        return connection.prepareStatement(sql);
    }
}
