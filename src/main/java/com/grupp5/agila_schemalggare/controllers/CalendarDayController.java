package com.grupp5.agila_schemalggare.controllers;

import com.grupp5.agila_schemalggare.models.Event;
import com.grupp5.agila_schemalggare.services.CalendarService;
import com.grupp5.agila_schemalggare.utils.DynamicController;
import com.grupp5.agila_schemalggare.utils.SceneManager;
import com.grupp5.agila_schemalggare.utils.SceneManagerProvider;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CalendarDayController implements DynamicController {
    @FXML
    private Label dayAndDateLabel;
    @FXML
    private VBox eventsContainer;
    @FXML
    public Button addEventButton;
    @FXML
    public Button returnButton;

    private final SceneManager sceneManager = SceneManagerProvider.getSceneManager();
    private final CalendarService calendarService = new CalendarService();
    private LocalDateTime currentDate;

    private void loadEventsForDate() {
        eventsContainer.getChildren().clear();

        List<Event> events = calendarService.getSpecificEvent(currentDate);

        for (Event event : events) {
            VBox eventModal = createEventModal(event);
            eventsContainer.getChildren().add(eventModal);
        }

        //add event button under list of daily events
        addEventButton.setOnAction(e -> sceneManager.openNewScene("/com/grupp5/agila_schemalggare/eventService.fxml", currentDate, "Create Event", 400, 300));
    }

    private VBox createEventModal(Event event) {
        VBox container = new VBox(10);

        // title row
        HBox titleRow = new HBox(10);
        titleRow.setAlignment(Pos.TOP_CENTER);

        Label titleLabel = new Label(event.getTitle());
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Button editButton = new Button("Edit event");
        editButton.setOnAction(e -> {
          FXMLLoader loader = sceneManager.openNewScene("/com/grupp5/agila_schemalggare/eventService.fxml", currentDate, "EditEvent", 400, 300);
          EventServiceController controller = loader.getController();
          controller.setCurrentEvent(event);
          controller.updateView();
        });

        Button deleteButton = new Button("Delete event");
        deleteButton.setOnAction(e -> deleteEvent(event));

        titleRow.getChildren().addAll(titleLabel , editButton, deleteButton );

        // time row
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

        // description label
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

    private void deleteEvent(Event event) {
        calendarService.deleteEvent(event);
    }

    @FXML
    public void returnToCalendar() { //optimally you'd return to the view you came from but for now always returns to month view
      ((Stage) returnButton.getScene().getWindow()).close();
    }

    @Override
    public void updateView() {
      dayAndDateLabel.setText(currentDate.toLocalDate().toString());
      dayAndDateLabel.setStyle("-fx-font-size: 20; -fx-font-weight: bold");
      loadEventsForDate();
    }

    @Override
    public void setCurrentDate(LocalDateTime currentDate) {
    this.currentDate = currentDate;
  }
}
