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

    public void insertMovie(Long movie_id, String user_id, String identifier, String title, String release_date, String movie_dsc, int is_favourite) {

        try {

            DatabaseConnection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.query("Movie");
            statement.setLong(1, movie_id);
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

    public void insertFutureList(String book_id, Long movie_id, String user_id, String identifier, String title, String author, String release_date, String movie_dsc) {

        try {
            DatabaseConnection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.query("Future_List");
            statement.setString(1, book_id);
            statement.setLong(2, movie_id);
            statement.setString(3, user_id);
            statement.setString(4, identifier);
            statement.setString(5, title);
            statement.setString(6, author);
            statement.setString(7, release_date);
            statement.setString(8, movie_dsc);
            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("A new" + identifier + "was inserted into FUTURE LIST successfully.");
            }

            connection.closeConnection();

        } catch (SQLException e) {
            System.out.println("Error inserting book: " + e.getMessage());
        }
    }

    public Set<Book> pullBooks() {

        Set<Book> books = new HashSet<>();

        try {

            DatabaseConnection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.query("Pull_Books");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                ArrayList<String> authorToList = new ArrayList<>();
                String isbn = rs.getString("book_id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                int is_favourite = rs.getInt("is_favourite");
                authorToList.add(author);

                Book book = new Book(title, authorToList, isbn, is_favourite);
                books.add(book);
            }


        } catch (SQLException e) {
            System.out.println("Error pulling Books: " + e.getMessage());

        }

        return books;
    }

    public Set<Movie> pullMovies() {

        Set<Movie> movies = new HashSet<>();

        try {

            DatabaseConnection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.query("Pull_Movies");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Long id = rs.getLong("movie_id");
                String title = rs.getString("title");
                String overview = rs.getString("movie_dsc");
                String release_date = rs.getString("release_date");
                int is_favourite = rs.getInt("is_favourite");

                Movie movie = new Movie(id, title, release_date, overview, is_favourite);
                movies.add(movie);
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

    public void addFavouriteBook(String book_id) {

        try {

            DatabaseConnection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.query("Add_FavouriteBook");
            statement.setString(1, book_id);
            statement.executeUpdate();

            System.out.println("Favourite book added");
            connection.closeConnection();

        } catch (SQLException e) {
            System.out.println("Error adding to favourite books: " + e.getMessage());

        }
    }

    public void removeFavouriteBook(String book_id) {

        try {

            DatabaseConnection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.query("Remove_FavouriteBook");
            statement.setString(1, book_id);
            statement.executeUpdate();

            System.out.println("Favourite book removed");
            connection.closeConnection();

        } catch (SQLException e) {
            System.out.println("Error removing from favourite books: " + e.getMessage());

        }
    }

    public void addFavouriteMovie(Long movie_id) {

        try {

            DatabaseConnection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.query("Add_FavouriteMovie");
            statement.setLong(1, movie_id);
            statement.executeUpdate();

            System.out.println("Favourite movie added");
            connection.closeConnection();

        } catch (SQLException e) {
            System.out.println("Error adding to favourite movies: " + e.getMessage());

        }
    }

    public void removeFavouriteMovie(Long movie_id) {

        try {

            DatabaseConnection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.query("Remove_FavouriteMovie");
            statement.setLong(1, movie_id);
            statement.executeUpdate();

            System.out.println("Favourite movie removed");
            connection.closeConnection();

        } catch (SQLException e) {
            System.out.println("Error removing from favourite movies: " + e.getMessage());

        }
    }

    public void removeBook(String book_id) {

        try {

            DatabaseConnection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.query("Delete_Book");
            statement.setString(1, book_id);
            int rowsDeleted = statement.executeUpdate();

            System.out.println(rowsDeleted + " row deleted.");
            connection.closeConnection();

        } catch (SQLException e) {
            System.out.println("Error removing book: " + e.getMessage());

        }
    }

    public void removeMovie(Long movie_id) {

        try {

            DatabaseConnection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.query("Delete_Movie");
            statement.setLong(1, movie_id);
            int rowsDeleted = statement.executeUpdate();

            System.out.println(rowsDeleted + " row deleted.");
            connection.closeConnection();

        } catch (SQLException e) {
            System.out.println("Error removing movie: " + e.getMessage());

        }
    }

    public void closeConnection(){

        try {

            DatabaseConnection connection = DatabaseConnection.getInstance();
            connection.closeConnection();

        } catch (SQLException e) {
            System.out.println("Error disconnecting: " + e.getMessage());

        }

    }

}
