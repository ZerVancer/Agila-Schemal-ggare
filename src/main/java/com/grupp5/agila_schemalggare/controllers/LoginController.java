package com.grupp5.agila_schemalggare.controllers;

import com.grupp5.agila_schemalggare.models.Account;
import com.grupp5.agila_schemalggare.services.AccountService;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class LoginController {

    private AccountService accountService;

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label statusLabel;



    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

       try {
           Account account = accountService.loginUser(username, password);

           if (account == null) {
               changeStatus("Incorrect username or password!" , "red");
               return;
           }

           changeStatus("Login Successful!" , "green");

       } catch (IllegalArgumentException exception) {
           changeStatus(exception.getMessage(), "red");
       }

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
