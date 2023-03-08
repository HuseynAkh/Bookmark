package home.Database;
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
    public PreparedStatement query(String type) throws SQLException {

        String sql = "null";

        if(type.equals("Book")){
            sql = "INSERT INTO my_book_list (user_id, title, author, genre, description) VALUES (?, ?, ?, ?, ?)";
        }else if(type.equals("Movie")){
            sql = "INSERT INTO my_movie_list (user_ID, title, release_date, description) VALUES (?, ?, ?, ?)";
        }//do nothing or error checking

        return connection.prepareStatement(sql);
    }
}
