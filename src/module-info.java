module com.example.hello {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires java.sql;


    opens home to javafx.fxml;
    exports home;
}