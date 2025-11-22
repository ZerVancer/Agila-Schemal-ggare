package com.grupp5.agila_schemalggare.controllers;

import com.grupp5.agila_schemalggare.services.AccountService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.InputMismatchException;

public class RegisterController {
    private AccountService accountService;

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @FXML
    TextField usernameInputField;
    @FXML
    PasswordField passwordInputField;
    @FXML
    Button submitButton;
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

        //changeSceneToLogin(); <-- empty method for implementation later
    }

    private void changeSceneToLogin() {}
}
