package home;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BookmarkApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BookmarkApplication.class.getResource("bookmark.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 1080);
        stage.setTitle("Bookmark");
        stage.setWidth(640);
        stage.setHeight(840);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}