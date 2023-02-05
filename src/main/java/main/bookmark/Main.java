package main.bookmark;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("bookmark.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 1920);
//        String css = this.getClass().getResource("application.css").toExternalForm();
//        scene.getStylesheets().add(css);
        stage.setTitle("Bookmark");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

