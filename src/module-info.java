module com.example.hello {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires json.simple;
    requires java.sql;
    requires java.desktop;
    requires org.testng;
    requires org.junit.jupiter.api;


    opens home.yorku.bookmarks.view to javafx.fxml;
    exports home.yorku.bookmarks.view;
    exports home.yorku.bookmarks.controller;
    opens home.yorku.bookmarks.controller to javafx.fxml;
}