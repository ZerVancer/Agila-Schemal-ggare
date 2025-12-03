package com.grupp5.agila_schemalggare.controllers;

import com.grupp5.agila_schemalggare.models.Event;
import com.grupp5.agila_schemalggare.services.CalendarService;
import com.grupp5.agila_schemalggare.utils.SceneManagerProvider;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDateTime;
import java.util.List;

public class CalendarDayController {
    @FXML
    private Label dayAndDateLabel;
    @FXML
    private VBox eventsContainer;
    @FXML
    public Button returnButton;

    CalendarService calendarService = new CalendarService();
    private LocalDateTime selectedDate;

    public void setDate(LocalDateTime date) {
        this.selectedDate = date;
        dayAndDateLabel.setText(date.toString());
        loadEventsForDate();
    }

    private void loadEventsForDate() {
        eventsContainer.getChildren().clear();

        List<Event> events = calendarService.getSpecificEvent(selectedDate);

        for (Event event : events) {
            VBox eventModal = createEventModal(event);
            eventsContainer.getChildren().add(eventModal);
        }
    }

    private VBox createEventModal(Event event) {
        VBox container = new VBox(10);

        HBox titleRow = new HBox(10);
        titleRow.setAlignment(Pos.TOP_CENTER);

        Label titleLabel = new Label(event.getTitle());
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        /*
        Button editButton = new Button("Edit event");
        editButton.setOnAction(e -> openEditEventWindow(event));

        Button deleteButton = new Button("Delete event");
        deleteButton.setOnAction(e -> deleteEvent(event));
        */

        titleRow.getChildren().addAll(titleLabel /*, editButton, deleteButton */);

        // === Time row ===
        HBox timeRow = new HBox(10);
        timeRow.setAlignment(Pos.TOP_CENTER);

        LocalDateTime startTime = event.getStartDate();
        LocalDateTime endTime = event.getEndDate();

        Label startTimeLabel = new Label(startTime.getHour() + ":" + startTime.getMinute());
        Label endTimeLabel = new Label(endTime.getHour() + ":" + endTime.getMinute());

        timeRow.getChildren().addAll(startTimeLabel, endTimeLabel);

        Label descriptionLabel = new Label(event.getDescription());
        descriptionLabel.setWrapText(true);

        container.getChildren().addAll(
                titleRow,
                timeRow,
                descriptionLabel
        );
        container.setAlignment(Pos.TOP_CENTER);

        return container;
    }


    @FXML
    public void returnToCalendar() { //optimally you'd return to the view you came from but for now always returns to month view
        SceneManagerProvider.getSceneManager().switchScene("/com/grupp5/agila_schemalggare/calendar-viex.fxml");
    }
}
