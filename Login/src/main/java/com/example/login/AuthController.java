package com.example.login;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class AuthController {

    @FXML private VBox loginPane;
    @FXML private VBox signUpPane;
    @FXML private VBox dashboardPane;

    @FXML private TextField loginUserField;
    @FXML private PasswordField loginPassField;
    @FXML private Label loginMessageLabel;

    @FXML private TextField signUpUserField;
    @FXML private PasswordField signUpPassField;
    @FXML private PasswordField signUpConfirmField;
    @FXML private Label signUpMessageLabel;

    @FXML private Label welcomeLabel;

    // Delegate all data & logic to UserManager
    private final UserManager userManager = new UserManager();

    @FXML
    private void handleLogin() {
        String user = loginUserField.getText().trim();
        String pass = loginPassField.getText();

        if (user.isEmpty() || pass.isEmpty()) {
            setMessage(loginMessageLabel, "Please fill in all fields.", true);
            return;
        }

        if (userManager.authenticate(user, pass)) {
            // Pulls directly from the active User object
            User loggedInUser = userManager.getCurrentUser();
            welcomeLabel.setText("Hello, " + loggedInUser.getUsername() + "!");

            loginUserField.clear();
            loginPassField.clear();
            loginMessageLabel.setText("");
            switchPane(dashboardPane);
        } else {
            setMessage(loginMessageLabel, "Invalid username or password.", true);
        }
    }

    @FXML
    private void handleSignUp() {
        String user = signUpUserField.getText().trim();
        String pass = signUpPassField.getText();
        String confirm = signUpConfirmField.getText();

        if (user.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {
            setMessage(signUpMessageLabel, "All fields are required.", true);
        } else if (!pass.equals(confirm)) {
            setMessage(signUpMessageLabel, "Passwords do not match.", true);
        } else if (!userManager.registerUser(user, pass)) {
            setMessage(signUpMessageLabel, "Username already exists.", true);
        } else {
            setMessage(signUpMessageLabel, "Account created! You can now log in.", false);
            signUpUserField.clear();
            signUpPassField.clear();
            signUpConfirmField.clear();
        }
    }

    @FXML
    private void handleLogout() {
        userManager.logout();
        switchPane(loginPane);
    }

    // ==================== NAVIGATION ====================

    @FXML
    private void showSignUp() {
        loginMessageLabel.setText("");
        switchPane(signUpPane);
    }

    @FXML
    private void showLogin() {
        signUpMessageLabel.setText("");
        switchPane(loginPane);
    }

    private void switchPane(VBox paneToShow) {
        loginPane.setVisible(paneToShow == loginPane);
        signUpPane.setVisible(paneToShow == signUpPane);
        dashboardPane.setVisible(paneToShow == dashboardPane);
    }

    private void setMessage(Label label, String text, boolean isError) {
        label.setText(text);
        label.setTextFill(isError ? Color.CRIMSON : Color.GREEN);
    }
}