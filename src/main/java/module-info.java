module com.example.bookmark {
    requires javafx.controls;
    requires javafx.fxml;


    opens main.bookmark to javafx.fxml;
    exports main.bookmark;
}