package com.grupp5.agila_schemalggare.controllers;

import com.grupp5.agila_schemalggare.models.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class RegisterController {
    @FXML
    TextField usernameInputField;

    @FXML
    TextField passwordInputField;

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

        User user = new User(username, password);
        //saveUserToFile(); <-- empty method for implementation later

        submitConfirmation.setText("Registration successful!");

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
