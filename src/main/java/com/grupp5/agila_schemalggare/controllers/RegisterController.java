package com.grupp5.agila_schemalggare.controllers;

import com.grupp5.agila_schemalggare.models.Account;
import com.grupp5.agila_schemalggare.models.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RegisterController {
    List<Account> accounts = new ArrayList<>(Arrays.asList(
            new User("username", "?password"),
            new User("milo_söder", "smörgåsrån4l!fe"),
            new User("hundenPollux", "p?nnarÄrBäst")
    ));

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


            if (!validateUsername(username)) {
                submitConfirmation.setText("Invalid username!\n" +
                        "Username cannot contain special characters or blank spaces,\n" +
                        "and be between 6 and 15 characters long.");
                usernameInputField.clear();
                passwordInputField.clear();
                return;
            }

            if (!validatePassword(password)) {
                submitConfirmation.setText("Invalid password!\n" +
                        "Password must contain at least one special character,\n" +
                        "and be between 6 and 30 characters long.");
                usernameInputField.clear();
                passwordInputField.clear();
                return;
            }

        Account account = new User(username, password);
        accounts.add(account);

        //saveUserToFile(); <-- empty method for implementation later

        StringBuilder testMessage = new StringBuilder("Registration successful!");

        for (Account a : accounts) {
            testMessage.append("\n").append(a.toString());
        }

        submitConfirmation.setText(testMessage.toString());
        //submitConfirmation.setText("Registration successful!");

        //changeSceneToLogin(); <-- empty method for implementation later
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

    private void changeSceneToLogin() {}
}
