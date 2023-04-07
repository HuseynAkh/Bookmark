package home.yorku.bookmarks.controller;

import home.yorku.bookmarks.controller.search.CoverUrlExtractor;
import home.yorku.bookmarks.model.Book;
import home.yorku.bookmarks.model.Movie;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

public class DecsriptionController {
    private BookmarkController bookmark;
    public DecsriptionController(BookmarkController bookmark){
        this.bookmark = bookmark;
    }

    public void descriptionType(String type, int index, Set<Book> bookSet, Set<Movie> movieSet) throws IOException {

        if (type.equals("Book")) {
            CoverUrlExtractor url = new CoverUrlExtractor();

            int i = 0;
            for (Book b : bookSet) {
                if (i == index) {
                    url.getBookCover(b.getIsbn());
                    setDescription(b.getTitle(), b.getIdentifier(), "Author(s): ", b.getAuthor().toString());

                    if (url.getBookCover(b.getIsbn())) {
                        InputStream stream = Files.newInputStream(Paths.get("./temporary.jpg"));
                        Image coverImage = new Image(stream);
                        bookmark.coverImageView.setImage(coverImage);
                        bookmark.coverImageView.setFitWidth(100);
                        bookmark.coverImageView.setFitHeight(200);
                    } else {
                        InputStream stream = Files.newInputStream(Paths.get("images/book-placeholder.jpg"));
                        Image coverImage = new Image(stream);
                        bookmark.coverImageView.setImage(coverImage);
                        bookmark.coverImageView.setFitWidth(100);
                        bookmark.coverImageView.setFitHeight(200);

                    }
                }
                i++;
            }
        }

        if (type.equals("Movie")) {

            int i = 0;
            for (Movie m : movieSet) {

                if (i == index) {
                    bookmark.descLabel.setText("Description: ");
                    bookmark.descLabel.setPadding(new Insets(1, 1, 5, 5));

                    if(m.getOverview() == null){
                        bookmark.desc.setText("*There is no description for this movie*");
                    } else {
                        bookmark.desc.setText(m.getOverview());
                    }

                    setDescription(m.getTitle(), m.getIdentifier(), "Release date: ", m.getReleaseDate());
                    bookmark.desc.setPadding(new Insets(5, 5, 5, 5));
                }
                i++;
            }
        }

    }

    // Responsible for setting, updating and formatting text labels and descriptions for movies and books
    private void setDescription(String title, String identifier, String dynamicLabel, String dynamicText) {

        bookmark.titleLabel.setText("Title: " + title);
        bookmark.titleLabel.setPadding(new Insets(1, 1, 5, 5));
        bookmark.idLabel.setText("Type: " + identifier);
        bookmark.idLabel.setPadding(new Insets(1, 1, 5, 5));

        if (identifier.equals("Book")) {
            String author = dynamicText.substring(1, dynamicText.length() - 1);
            bookmark.authorOrRelease.setText(dynamicLabel + author);
        } else {
            bookmark.authorOrRelease.setText(dynamicLabel + ": " + dynamicText);
        }
        bookmark.authorOrRelease.setPadding(new Insets(1, 1, 5, 5));

    }

    protected void clear() {

        bookmark.titleLabel.setText("");
        bookmark.idLabel.setText("");
        bookmark.authorOrRelease.setText("");
        bookmark.descLabel.setText("");
        bookmark.desc.setText("");
        bookmark.coverImageView.setImage(null);

    }
}
