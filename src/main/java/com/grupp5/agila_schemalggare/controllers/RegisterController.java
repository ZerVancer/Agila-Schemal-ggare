package com.grupp5.agila_schemalggare.controllers;

import com.grupp5.agila_schemalggare.models.User;
import com.grupp5.agila_schemalggare.services.AccountService;
import com.grupp5.agila_schemalggare.utils.SceneManagerProvider;
import com.grupp5.agila_schemalggare.utils.ServiceRegister;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class RegisterController implements ServiceRegister {
    private AccountService accountService;

    @Override
    public void registerAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @FXML
    TextField usernameInputField;
    @FXML
    PasswordField passwordInputField;
    @FXML
    Button submitButton;
    @FXML
    Button changeToLogin;
    @FXML
    Label submitConfirmation;

    @FXML
    private void submitUserDetails() {
        String username =  usernameInputField.getText();
        String password = passwordInputField.getText();

        boolean creationSuccess;

        try {
            creationSuccess = accountService.registerNewAccount(username, password);
        } catch (Exception exception) {
            submitConfirmation.setTextFill(Color.RED);
            submitConfirmation.setText(exception.getMessage());

            usernameInputField.clear();
            passwordInputField.clear();
            return;
        }

        if (creationSuccess) {
            submitConfirmation.setTextFill(Color.GREEN);
            submitConfirmation.setText("Account creation success!");
        }

        changeSceneToLogin();
    }

    @FXML
    private void changeSceneToLogin() {
        SceneManagerProvider.getSceneManager().switchScene("/com/grupp5/agila_schemalggare/login-view.fxml");
    }

    private boolean validateUsername(String username) { //later also validate that no two users can have the same username
        return username.length() > 5 && //longer than 5 characters
                username.length() < 16 && //shorter than 16 characters
                username.matches("[A-Za-z0-9_.-]+"); //allows letters, numbers, -, _, and .
    }

    private boolean validatePassword(String password) {
        return password.length() > 5 && //longer than 5 characters
                password.length() < 31 && //shorter than 31
                password.matches(".*[!@#$%^&*()_+=\\-{}\\[\\]:;\"'<>,.?/].*"); //must contain at least one special character
    }

    private void saveUserToFile(User user) {}

}
