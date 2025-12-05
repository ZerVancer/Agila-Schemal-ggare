package com.grupp5.agila_schemalggare.controllers;

import com.grupp5.agila_schemalggare.models.Account;
import com.grupp5.agila_schemalggare.models.Event;
import com.grupp5.agila_schemalggare.services.AccountService;
import com.grupp5.agila_schemalggare.services.CalendarService;
import com.grupp5.agila_schemalggare.utils.SceneManagerProvider;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import java.time.LocalDateTime;

public class SideMenuController {
    private CalendarViewController calendarViewController;
    private CalendarService calendarService = new CalendarService();

    @FXML
    private Label loggedInUser;
    @FXML
    private Button adminButton;
    @FXML
    private Button logOutButton;
    @FXML
    private Button renderWeekly;
    @FXML
    private Button renderMonthly;
    @FXML
    private Button renderYearly;

    @FXML
    private GridPane currentEventsGrid;

    private Button activeButton;

    public void initialize() {
        updateUserLoggedIn();
        renderTheEvents();
    }

    private void updateUserLoggedIn() {
        Account account = AccountService.getLoggedInAccount();

        if (account != null) {
            loggedInUser.setText("User: " + account.getUsername());
        } else {
            loggedInUser.setText("");
        }

        if (account.getRole().equals("USER")) {
            adminButton.setVisible(false);
            adminButton.setManaged(false);
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

    public void handleAdminClick() {
        switchSceneToAdminMenu();
    }

    public void switchSceneToLogin() {
        AccountService.setLoggedInAccount(null);

        if (AccountService.getLoggedInAccount() == null) {
            SceneManagerProvider.getSceneManager().switchScene("/com/grupp5/agila_schemalggare/login-view.fxml", LocalDateTime.now());
        }
    }

    public void switchSceneToAdminMenu() {
        SceneManagerProvider.getSceneManager().switchScene("/com/grupp5/agila_schemalggare/adminMenu-view.fxml");
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

    private List<Event> getThreeEvents() {
        LocalDateTime today = LocalDateTime.now();

        List<Event> events = calendarService.getAllEvents();

        List<Event> threeEvents = events.stream()
                .filter(event -> !event.getStartDate().isBefore(today))
                .sorted((event1, event2) -> event1.getStartDate().compareTo(event2.getStartDate()))
                .limit(3)
                .collect(Collectors.toList());

        return threeEvents;
    }

    // Uppdatera med dynamiccontroller för att gör allt dynamiskt och uppdaterbart i realtid.
    @FXML
    private void renderTheEvents() {
        currentEventsGrid.getChildren().clear();

        List<Event> events = getThreeEvents();

        for (int i = 0; i < events.size(); i++) {
            Event event = events.get(i);
            VBox eventCard = createEventCard(event);
            currentEventsGrid.add(eventCard, 0, i);
        }
    }

    private VBox createEventCard(Event event) {
        VBox eventCard = new VBox();
        eventCard.setSpacing(4);
        eventCard.setStyle("-fx-padding: 10; -fx-border-color: gray; -fx-border-radius: 5; -fx-background-radius: 5; -fx-background-color: #f0f0f0; -fx-end-margin: 5px;");

        Label titleLabel = new Label(event.getTitle());
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        Label startTimeLabel = new Label(event.getStartDate().format(formatter));

        Label descriptionLabel = new Label(event.getDescription());
        descriptionLabel.setWrapText(true);

        eventCard.getChildren().addAll(titleLabel, startTimeLabel, descriptionLabel);

        return eventCard;
    }
}
