package home.yorku.bookmarks.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class BookmarkApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(BookmarkApplication.class.getResource("bookmark.fxml"));
        URL fxmlLocation = this.getClass().getResource("bookmark.fxml");
        System.out.println("fxmlLocation: "+fxmlLocation);
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
        //FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/bookmark.fxml"));

        System.out.println("fxml loaded");
        //fxmlLoader.setRoot(new AnchorPane());
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Bookmark");
        stage.setWidth(890);
        stage.setHeight(785);
        stage.setScene(scene);
        stage.show();
        //
        //
        //Scene scene = new Scene(fxmlLoader.load(), 1080, 1920);
        //Scene scene = new Scene(fxmlLoader.load(), 1080, 1920);
        //String css = this.getClass().getResource("application.css").toExternalForm();
        //scene.getStylesheets().add(css);
        //stage.setTitle("Bookmark");
        //stage.setScene(scene);
        //stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}