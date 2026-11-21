package com.example.login;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class AuthController {

    // Panes
    @FXML private VBox loginPane;
    @FXML private VBox signUpPane;
    @FXML private VBox dashboardPane;

    // Login Controls
    @FXML private TextField loginUserField;
    @FXML private PasswordField loginPassField;
    @FXML private Label loginMessageLabel;

    // Sign-Up Controls
    @FXML private TextField signUpUserField;
    @FXML private PasswordField signUpPassField;
    @FXML private PasswordField signUpConfirmField;
    @FXML private Label signUpMessageLabel;

    // Dashboard Controls
    @FXML private Label welcomeLabel;

    // User storage & local file name
    private final Map<String, String> userDatabase = new HashMap<>();
    private static final String FILE_PATH = "users.properties";

    @FXML
    public void initialize() {
        loadUsersFromFile();

        // Add default admin account only if no users exist yet
        if (userDatabase.isEmpty()) {
            userDatabase.put("admin", "1234");
            saveUsersToFile();
        }
    }

    // ==================== FILE STORAGE METHODS ====================

    private void loadUsersFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;

        Properties props = new Properties();
        try (FileInputStream in = new FileInputStream(file)) {
            props.load(in);
            for (String username : props.stringPropertyNames()) {
                userDatabase.put(username, props.getProperty(username));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveUsersToFile() {
        Properties props = new Properties();
        props.putAll(userDatabase);

        try (FileOutputStream out = new FileOutputStream(FILE_PATH)) {
            props.store(out, "Saved User Accounts");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ==================== BUTTON ACTIONS ====================

    @FXML
    private void handleLogin() {
        String user = loginUserField.getText().trim();
        String pass = loginPassField.getText();

        if (user.isEmpty() || pass.isEmpty()) {
            setMessage(loginMessageLabel, "Please fill in all fields.", true);
        } else if (userDatabase.containsKey(user) && userDatabase.get(user).equals(pass)) {
            welcomeLabel.setText("Hello, " + user + "!");
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
        } else if (userDatabase.containsKey(user)) {
            setMessage(signUpMessageLabel, "Username already exists.", true);
        } else if (!pass.equals(confirm)) {
            setMessage(signUpMessageLabel, "Passwords do not match.", true);
        } else {
            userDatabase.put(user, pass);
            saveUsersToFile(); // Saves immediately to users.properties!
            setMessage(signUpMessageLabel, "Account created! You can now log in.", false);
            signUpUserField.clear();
            signUpPassField.clear();
            signUpConfirmField.clear();
        }
    }

    @FXML
    private void handleLogout() {
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
