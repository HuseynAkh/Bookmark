package home.yorku.bookmarks.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class BookmarkApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(BookmarkApplication.class.getResource("/bookmark.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 890, 785);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/stylesheet.css")).toExternalForm());

        URL fxmlLocation = this.getClass().getResource("/bookmark.fxml");
        System.out.println("fxmlLocation: " + fxmlLocation);
        System.out.println("fxml loaded");

        stage.setTitle("Bookmark");
        stage.setWidth(900);
        stage.setHeight(680);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}