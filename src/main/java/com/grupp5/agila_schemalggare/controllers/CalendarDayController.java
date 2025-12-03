package com.grupp5.agila_schemalggare.controllers;

import com.grupp5.agila_schemalggare.models.Event;
import com.grupp5.agila_schemalggare.services.CalendarService;
import com.grupp5.agila_schemalggare.utils.SceneManagerProvider;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
      dayAndDateLabel.setText(date.toLocalDate().toString());
        dayAndDateLabel.setStyle("-fx-font-size: 20; -fx-font-weight: bold");
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

        Button editButton = new Button("Edit event");
        editButton.setOnAction(e -> openEditEventWindow(event));

        Button deleteButton = new Button("Delete event");
        deleteButton.setOnAction(e -> deleteEvent(event));

        titleRow.getChildren().addAll(titleLabel , editButton, deleteButton );

        // === Time row ===
        HBox timeRow = new HBox(5);
        timeRow.setAlignment(Pos.TOP_CENTER);

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        LocalDateTime startTime = event.getStartDate();
        LocalDateTime endTime = event.getEndDate();

        String startTimeFormatted = startTime.format(timeFormatter);
        String endTimeFormatted = endTime.format(timeFormatter);

        Label startTimeLabel = new Label(startTimeFormatted + " - ");
        Label endTimeLabel = new Label(endTimeFormatted);

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

    private void openEditEventWindow(Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/grupp5/agila_schemalggare/eventService.fxml"));
            Parent root = loader.load();

            EventServiceController controller = loader.getController();
            controller.setCurrentEvent(event);
            controller.setCurrentDate(selectedDate);
            controller.setScene();

            Stage stage = new Stage();
            stage.setTitle("Edit Event");
            stage.setScene(new Scene(root));
            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteEvent(Event event) {
        calendarService.deleteEvent(event);
    }

    @FXML
    public void returnToCalendar() { //optimally you'd return to the view you came from but for now always returns to month view
        SceneManagerProvider.getSceneManager().switchScene("/com/grupp5/agila_schemalggare/calendar-viex.fxml");
    }
}
