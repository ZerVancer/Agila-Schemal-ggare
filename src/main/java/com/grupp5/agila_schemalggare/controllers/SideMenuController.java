package com.grupp5.agila_schemalggare.controllers;

import com.grupp5.agila_schemalggare.models.Account;
import com.grupp5.agila_schemalggare.services.AccountService;
import com.grupp5.agila_schemalggare.utils.SceneManagerProvider;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.time.LocalDateTime;

public class SideMenuController {
    private CalendarViewController calendarViewController;
    @FXML
    private Label loggedInUser;
    @FXML
    private Button logOutButton;
    @FXML
    private Button renderWeekly;
    @FXML
    private Button renderMonthly;
    @FXML
    private Button renderYearly;

    private Button activeButton;

    public void initialize() {
        updateUserLoggedIn();
    }

    private void updateUserLoggedIn() {
        Account account = AccountService.getLoggedInAccount();

        if (account != null) {
            loggedInUser.setText("User: " + account.getUsername());
        } else {
            loggedInUser.setText("");
        }
    }

    public void setCalendarViewController(CalendarViewController controller) {
        this.calendarViewController = controller;
        setActiveButton(renderYearly);
    }

    @FXML
    private void handleWeeklyClick() {
        calendarViewController.showWeekView();
        setActiveButton(renderWeekly);
    }

    @FXML
    public void handleMonthlyClick() {
        calendarViewController.showMonthView();
        setActiveButton(renderMonthly);
    }

    // Nu togglar den bara activeButton
    @FXML
    public void handleYearlyClick() {
        calendarViewController.showYearView();
        setActiveButton(renderYearly);
    }

    @FXML
    public void handleLogoutClick() {
        switchSceneToLogin();
    }

    public void switchSceneToLogin() {
        AccountService.setLoggedInAccount(null);

        if (AccountService.getLoggedInAccount() == null) {
            SceneManagerProvider.getSceneManager().switchScene("/com/grupp5/agila_schemalggare/login-view.fxml", LocalDateTime.now());
        }
    }

    private void setActiveButton(Button clickedButton) {
        if (activeButton != null) {
            activeButton.getStyleClass().remove("active");
            activeButton.setStyle("");
        }

        activeButton = clickedButton;

        activeButton.getStyleClass().add("active");
        activeButton.setStyle("-fx-background-color: lightblue; -fx-border-color: gray; -fx-border-radius: 4px; -fx-background-radius: 4px;");
    }
}
