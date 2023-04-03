module com.home.yorku.bookmarks{
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires json.simple;
    requires java.sql;
    requires java.desktop;
    requires org.testng;
    requires org.mariadb.jdbc;

    exports home.yorku.bookmarks.model;
    exports home.yorku.bookmarks.view;
    exports home.yorku.bookmarks.controller;
    opens home.yorku.bookmarks.controller to javafx.fxml;
}