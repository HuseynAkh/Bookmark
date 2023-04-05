package home.yorku.bookmarks.controller;

import home.yorku.bookmarks.controller.database.ConnectionMethods;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Optional;

public class CreateUserController {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private VBox centerBox;
    @FXML
    private TextField usernameCrtTxt;
    @FXML
    private TextField passwordCrtTxt;
    @FXML
    private TextField checkPassword;
    @FXML
    private TextField emailCrtTxt;
    private double sceneHeight;
    private double sceneWidth;

    @FXML
    private void initialize(){
        Scene scene = anchorPane.getScene();
        if (scene != null) {
            sceneHeight = scene.getHeight();
            sceneWidth = scene.getWidth();
        }

        // All dynamic login box layouts
        anchorPane.sceneProperty().addListener((observable, oldScene, newScene) -> {

            if (newScene != null) {
                newScene.heightProperty().addListener((obs, oldHeight, newHeight) -> {
                    sceneHeight = newHeight.doubleValue();
                    centerBox.setLayoutY((sceneHeight/2) - (20 + centerBox.getHeight()/2)); // 20 for padding between boxes

                });

                newScene.widthProperty().addListener((obs, oldWidth, newWidth) -> {
                    sceneWidth = newWidth.doubleValue();
                    centerBox.setLayoutX((sceneWidth/2) - (centerBox.getWidth()/2));

                });
            }

        });
    }

    @FXML
    private void addUser() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Account successfully created!");
        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(okButton);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == okButton) {
            Stage stage = (Stage) centerBox.getScene().getWindow();
            stage.close();
        }
    }

    public void createAccount() {

        ConnectionMethods method = new ConnectionMethods();

        String username = usernameCrtTxt.getText();
        String password = passwordCrtTxt.getText();
        String checkPass = checkPassword.getText();
        String email = emailCrtTxt.getText();

        ArrayList<String> pleaseEnter = new ArrayList<>();

        if(!username.equals("") && !password.equals("") && !checkPass.equals("") && !email.equals("")){

            if(password.equals(checkPass)){

                int check = method.insertUser(username, password, email);

                if(check == 0){
                    addUser();
                } else if (check == 1) {
                    System.out.println("Username already used");
                    method.closeConnection();
                } else if (check == 2) {
                    System.out.println("email already used");
                    method.closeConnection();
                } else {
                    System.out.println("something else");
                    method.closeConnection();
                }

            } else {

                showError("DNM", pleaseEnter);
            }

        } else {

            if(usernameCrtTxt.getText().isEmpty()){
                pleaseEnter.add(" a username");
            }

            if (passwordCrtTxt.getText().isEmpty()){
                pleaseEnter.add(" a password");
            }

            if (emailCrtTxt.getText().isEmpty()){
                pleaseEnter.add(" an email");
            }

            if(checkPassword.getText().isEmpty() && pleaseEnter.size() == 0){

                showError("ReEnter", pleaseEnter);

            } else if(checkPassword.getText().isEmpty() && pleaseEnter.size() > 0){

                showError("Mix", pleaseEnter);

            } else {

                showError("All", pleaseEnter);
            }
        }
    }

    private void showError(String error, ArrayList<String> errors){

        String errorMsg = errors.toString();
        errorMsg = errorMsg.substring(1, errorMsg.length() - 1);

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Creating Account");
        alert.setHeaderText(null);

        switch(error){
            case "All": {
                alert.setContentText("Please enter" + errorMsg + " to create an account");
                break;
            }
            case "Mix": {
                alert.setContentText("Please enter" + errorMsg + " and re-enter your password to create an account");
                break;
            }
            case "ReEnter": {
                alert.setContentText("Please re-enter your password to create an account");
                break;
            }
            case "DNM": {
                alert.setContentText("Passwords do not match");
                break;
            }

        }

        alert.showAndWait();
    }
}
