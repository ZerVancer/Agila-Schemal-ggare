package com.grupp5.agila_schemalggare.controllers;

import com.grupp5.agila_schemalggare.models.Account;
import com.grupp5.agila_schemalggare.services.AccountService;
import com.grupp5.agila_schemalggare.utils.SceneManager;
import com.grupp5.agila_schemalggare.utils.SceneManagerProvider;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.time.LocalDateTime;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label statusLabel;

    private final SceneManager sceneManager = SceneManagerProvider.getSceneManager();
    private final AccountService accountService = new AccountService();

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

        switchToCalendarView();
    }

    // För framtida användning om användaren vill byta vy från login menyn för att registrera ett konto
    @FXML
    private void switchToRegisterView() {
      sceneManager.switchScene("/com/grupp5/agila_schemalggare/register-view.fxml");
    }

    private void switchToCalendarView() {
        sceneManager.switchScene("/com/grupp5/agila_schemalggare/calendar-viex.fxml", LocalDateTime.now());
    }

    private void changeStatus(String message, String color) {
        statusLabel.setText(message);
        statusLabel.setStyle("-fx-text-fill: " + color);
        statusLabel.setVisible(true);
    }
}
