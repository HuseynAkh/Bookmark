module com.example.hello {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires java.sql;
    requires java.desktop;


    opens home to javafx.fxml;
    exports home;
    exports home.Controllers;
    opens home.Controllers to javafx.fxml;
}