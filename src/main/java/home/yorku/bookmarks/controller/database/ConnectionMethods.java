package home.yorku.bookmarks.controller.database;

import home.yorku.bookmarks.model.Book;
import home.yorku.bookmarks.model.BookToPortfolio;
import home.yorku.bookmarks.model.Movie;
import home.yorku.bookmarks.model.MovieToPortfolio;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ConnectionMethods {

    public Integer insertUser(String username, String password, String email) {

        int exitCode = 0;

        try {
            DatabaseConnection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.query("Insert_User");
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, email);
            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("A user has successfully been added");
            }

            connection.closeConnection();

        } catch (SQLException e) {

            if (e.getErrorCode() == 1062) { // Duplication error code "1062"
                String errorMessage = e.getMessage().toLowerCase();
                if (errorMessage.contains("primary")) {
                    exitCode = 1;
                } else if (errorMessage.contains("email")) {
                    exitCode = 2;
                } else {
                    exitCode = -1;
                    System.out.println("Duplication error " + e.getMessage());
                }
            } else {
                exitCode = -1;
                System.out.println("Error adding user " + e.getMessage());
            }

        }

        return exitCode;
    }

    public Integer checkCrd(String username, String password){

        int verified = 0;

        try {

            DatabaseConnection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.query("Check_Crd");
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                verified = rs.getInt("user_verified");
            }

        } catch (SQLException e) {
            System.out.println("Error check user verification: " + e.getMessage());
        }

        return verified;
    }

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

        } catch (SQLException e) {
            System.out.println("Error inserting book: " + e.getMessage());
        }
    }

    public void insertMovie(Long movie_id, String user_id, String genre, String identifier, String title, String release_date, String movie_dsc, int is_favourite) {

        try {

            DatabaseConnection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.query("Movie");
            statement.setLong(1, movie_id);
            statement.setString(2, user_id);
            statement.setString(3, genre);
            statement.setString(4, identifier);
            statement.setString(5, title);
            statement.setString(6, release_date);
            statement.setString(7, movie_dsc);
            statement.setInt(8, is_favourite);

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("A new movie was inserted into MOVIE LIST successfully.");
            }

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

    public ArrayList<String> pullBookIds(String user_id) {

        ArrayList<String> bookIds = new ArrayList<String>();

        try {

            DatabaseConnection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.query("Pull_BookIds");
            statement.setString(1, user_id);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {

                String isbn = rs.getString("book_id");
                bookIds.add(isbn);
            }

        } catch (SQLException e) {
            System.out.println("Error pulling Books: " + e.getMessage());

        }

        return bookIds;
    }

    public ArrayList<Long> pullMovieIds(String user_id) {

        ArrayList<Long> movieIds = new ArrayList<Long>();

        try {

            DatabaseConnection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.query("Pull_MovieIds");
            statement.setString(1, user_id);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {

                Long movie_id = rs.getLong("movie_id");
                movieIds.add(movie_id);
            }

        } catch (SQLException e) {
            System.out.println("Error pulling Books: " + e.getMessage());

        }

        return movieIds;
    }

    public Set<BookToPortfolio> pullBooks(String user_id) {

        Set<BookToPortfolio> books = new HashSet<>();

        try {

            DatabaseConnection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.query("Pull_Books");
            statement.setString(1, user_id);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                ArrayList<String> authorToList = new ArrayList<>();
                String isbn = rs.getString("book_id");
                String user = rs.getString("user_id");
                String identifier = rs.getString("identifier");
                String title = rs.getString("title");
                String author = rs.getString("author");
                int is_favourite = rs.getInt("is_favourite");
                authorToList.add(author);

                BookToPortfolio book = new BookToPortfolio(isbn, user, identifier, title, authorToList, is_favourite);
                books.add(book);
            }


        } catch (SQLException e) {
            System.out.println("Error pulling Books: " + e.getMessage());

        }

        return books;
    }

    public Set<MovieToPortfolio> pullMovies(String user_id) {

        Set<MovieToPortfolio> movies = new HashSet<>();

        try {

            DatabaseConnection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.query("Pull_Movies");
            statement.setString(1, user_id);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                ArrayList<Long> genreToList = new ArrayList<>();

                Long id = rs.getLong("movie_id");
                String user = rs.getString("user_id");
                String genre = rs.getString("genre");
                String identifier = rs.getString("identifier");
                String title = rs.getString("title");
                String release_date = rs.getString("release_date");
                String overview = rs.getString("movie_dsc");
                int is_favourite = rs.getInt("is_favourite");

                String[] genreArray = genre.split(",");

                for (String genreString : genreArray) {

                    if(!genreString.isEmpty()){
                        Long genreLong = Long.parseLong(genreString.trim());
                        genreToList.add(genreLong);
                    } else {
                        genreToList.add(null);
                    }


                }

                MovieToPortfolio movie = new MovieToPortfolio(id, user, genreToList, identifier, title, release_date, overview, is_favourite);
                movies.add(movie);
            }

        } catch (SQLException e) {
            System.out.println("Error pulling Movies: " + e.getMessage());

        }

        return movies;
    }

    public ArrayList<String> pullFutureList(String user_id) {

        ArrayList<String> futureList = new ArrayList<>();

        try {

            DatabaseConnection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.query("Pull_Future_List");
            statement.setString(1, user_id);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String title = rs.getString("title");
                futureList.add(title);
            }

            connection.closeConnection();

        } catch (SQLException e) {
            System.out.println("Error inserting into FUTURE LIST: " + e.getMessage());

        }

        return futureList;
    }

    public void removeFutureList(String title, String user_id){
        try {

            DatabaseConnection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.query("Delete_Future_List");
            statement.setString(1, title);
            statement.setString(2, user_id);
            int rowsDeleted = statement.executeUpdate();

            System.out.println(rowsDeleted + " row deleted.");
            connection.closeConnection();

        } catch (SQLException e) {
            System.out.println("Error removing item from MY FUTURE LIST: " + e.getMessage());

        }
    }

    public void addFavouriteBook(String book_id, String user_id) {

        try {

            DatabaseConnection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.query("Add_FavouriteBook");
            statement.setString(1, book_id);
            statement.setString(2, user_id);
            statement.executeUpdate();

            System.out.println("Favourite book added");

        } catch (SQLException e) {
            System.out.println("Error adding to favourite books: " + e.getMessage());

        }
    }

    public void removeFavouriteBook(String book_id, String user_id) {

        try {

            DatabaseConnection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.query("Remove_FavouriteBook");
            statement.setString(1, book_id);
            statement.setString(2, user_id);
            statement.executeUpdate();

            System.out.println("Favourite book removed");

        } catch (SQLException e) {
            System.out.println("Error removing from favourite books: " + e.getMessage());

        }
    }

    public void removeBook(String book_id, String user_id) {

        try {

            DatabaseConnection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.query("Delete_Book");
            statement.setString(1, book_id);
            statement.setString(2, user_id);
            int rowsDeleted = statement.executeUpdate();

            System.out.println(rowsDeleted + " row deleted.");

        } catch (SQLException e) {
            System.out.println("Error removing book: " + e.getMessage());

        }
    }

    public void addFavouriteMovie(Long movie_id, String user_id) {

        try {

            DatabaseConnection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.query("Add_FavouriteMovie");
            statement.setLong(1, movie_id);
            statement.setString(2, user_id);
            statement.executeUpdate();

            System.out.println("Favourite movie added");

        } catch (SQLException e) {
            System.out.println("Error adding to favourite movies: " + e.getMessage());

        }
    }

    public void removeFavouriteMovie(Long movie_id, String user_id) {

        try {

            DatabaseConnection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.query("Remove_FavouriteMovie");
            statement.setLong(1, movie_id);
            statement.setString(2, user_id);
            statement.executeUpdate();

            System.out.println("Favourite movie removed");

        } catch (SQLException e) {
            System.out.println("Error removing from favourite movies: " + e.getMessage());

        }
    }

    public void removeMovie(Long movie_id, String user_id) {

        try {

            DatabaseConnection connection = DatabaseConnection.getInstance();
            PreparedStatement statement = connection.query("Delete_Movie");
            statement.setLong(1, movie_id);
            statement.setString(2, user_id);
            int rowsDeleted = statement.executeUpdate();

            System.out.println(rowsDeleted + " row deleted.");

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
