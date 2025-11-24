package com.grupp5.agila_schemalggare.controllers;

import com.grupp5.agila_schemalggare.ScheduleApplication;
import com.grupp5.agila_schemalggare.models.Account;
import com.grupp5.agila_schemalggare.services.AccountService;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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

    @FXML
    private void switchToRegisterView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/grupp5/agila_schemalggare/register-view.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.getScene().setRoot(root);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

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
