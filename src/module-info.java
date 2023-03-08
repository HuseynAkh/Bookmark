module com.example.hello {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires java.sql;
    requires java.desktop;


    opens home to javafx.fxml;
    exports home;
}