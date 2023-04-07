package home.yorku.bookmarks.controller;

import home.yorku.bookmarks.controller.database.ConnectionMethods;
import javafx.event.EventHandler;
import javafx.scene.control.Tab;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class LoginController {

    private BookmarkController bookmark;
    public LoginController(BookmarkController bookmark){
        this.bookmark = bookmark;
    }
    protected boolean userLogin(String username, String password){

        ConnectionMethods method = new ConnectionMethods();

        if (!username.equals("") && !password.equals("")) {

            int verify = method.checkCrd(username, password);

            if (verify == 1) {

                bookmark.validUserId = username;
                bookmark.logout = false;
                bookmark.stage = (Stage) bookmark.tabPane.getScene().getWindow();
                bookmark.stage.setWidth(900);
                bookmark.stage.setHeight(680);
                setLoginPane();

                return true;

            } else {
                // Later feature add for get password or prompt user to creat account
                bookmark.LoginError.setTextFill(Color.RED);
                bookmark.LoginError.setText("Invalid user name or password");

            }

        } else if (bookmark.usernameTxt.getText().isEmpty() && !bookmark.passwordTxt.getText().isEmpty()) {

            bookmark.LoginError.setTextFill(Color.RED);
            bookmark.LoginError.setText("Please enter a username to Login");

        } else if (!bookmark.usernameTxt.getText().isEmpty() && bookmark.passwordTxt.getText().isEmpty()) {

            bookmark.LoginError.setTextFill(Color.RED);
            bookmark.LoginError.setText("Please enter a password to Login");

        } else {
            bookmark.LoginError.setTextFill(Color.RED);
            bookmark.LoginError.setText("Please enter a username & password to Login");
        }

        return false;
    }

    private void setLoginPane(){

        bookmark.tabPane.getTabs().addAll(bookmark.removedTabs);
        bookmark.removedTabs.clear();

        for (Tab tab : bookmark.tabPane.getTabs()) {
            tab.setDisable(false);
        }

        // Enable and show the login tab
        Tab loginTab = bookmark.tabPane.getTabs().stream()
                .filter(tab -> tab.getId().equals("LoginPane"))
                .findFirst()
                .orElse(null);
        bookmark.removedTabs.add(loginTab);
        bookmark.tabPane.getTabs().remove(loginTab);
    }

}
