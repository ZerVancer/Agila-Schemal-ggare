package com.grupp5.agila_schemalggare.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class SideMenuController {
    private CalendarViewController calendarViewController;

    @FXML
    private Button renderWeekly;
    @FXML
    private Button renderMonthly;
    @FXML
    private Button renderYearly;

    private Button activeButton;

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
