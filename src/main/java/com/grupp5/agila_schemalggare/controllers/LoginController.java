package com.grupp5.agila_schemalggare.controllers;

import com.grupp5.agila_schemalggare.models.Account;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label statusLabel;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty()) {
            changeStatus("Username is required!", "red");
            return;
        }

        if (password.isEmpty()) {
            changeStatus("Password is required!", "red");
            return;
        }

        // Måste kolla så att kontot faktiskt finns sparat i en arraylist senare?
        // Måste uppdateras efter register fungerar samt User/Admin klasserna finns.
        // just nu skapar jag en instans av Account baserat på input från view, ta bort "abstract" för att kunna göra det i main.
        Account account = new Account(username, password);

        if (account == null) {
            changeStatus("Username not found!" , "red");
            return;
        }

        if (!account.getPassword().equals(password)) {
            changeStatus("Incorrect password!" , "red");
            return;
        }

        changeStatus("Login successful!", "green");

        // Timer för att kalla på en funktion som senare ska rendera kalendern efter lyckad inloggning.
        // 2 sekunder delay för att simulera en sökning av kontot.
        // Kan ta bort senare, samt clearFields metoden.
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> clearFields()));
        timeline.setCycleCount(1);
        timeline.play();
    }

    // För framtida användning om användaren vill byta vy från login menyn för att registrera ett konto
//    @FXML
//    private void switchToRegisterView() {}

    private void changeStatus(String message, String color) {
        statusLabel.setText(message);
        statusLabel.setStyle("-fx-text-fill: " + color);
        statusLabel.setVisible(true);
    }

    private void clearFields() {
        usernameField.clear();
        passwordField.clear();
    }
}
