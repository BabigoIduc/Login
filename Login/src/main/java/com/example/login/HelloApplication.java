package com.example.login;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application { // Keep your existing class name if different
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("AuthView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 350, 420);
        stage.setTitle("JavaFX FXML Auth");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
