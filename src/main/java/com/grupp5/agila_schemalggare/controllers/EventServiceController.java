package com.grupp5.agila_schemalggare.controllers;

import com.grupp5.agila_schemalggare.models.Event;
import com.grupp5.agila_schemalggare.services.AccountService;
import com.grupp5.agila_schemalggare.services.CalendarService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.time.LocalDateTime;

public class EventServiceController {
    @FXML
    private TextField titleField;
    @FXML
    private TextField descriptionField;
    @FXML
    private Spinner<Integer> startTimeHourSpinner;
    @FXML
    private Spinner<Integer> startTimeMinuteSpinner;
    @FXML
    private Spinner<Integer> endTimeHourSpinner;
    @FXML
    private Spinner<Integer> endTimeMinuteSpinner;
    @FXML
    private Label errorMessage;

    // Change to accountService.getCurrentEvent and accountService.getCurrentDate in Initialize
    private Event currentEvent;
    private LocalDateTime currentDate;
    private final CalendarService calenderService = new CalendarService();

    public void setCurrentEvent(Event currentEvent) {
        this.currentEvent = currentEvent;
    }

    public void setCurrentDate(LocalDateTime currentDate) {
        this.currentDate = currentDate;
    }

    @FXML
    public void confirmButton(ActionEvent event) {
        String title = titleField.getText();
        String description = descriptionField.getText();
        int startTimeHourSpinnerValue = startTimeHourSpinner.getValue();
        int startTimeMinuteSpinnerValue = startTimeMinuteSpinner.getValue();
        int endTimeHourSpinnerValue = endTimeHourSpinner.getValue();
        int endTimeMinuteSpinnerValue = endTimeMinuteSpinner.getValue();

        if (title.isEmpty()) {
            errorMessage.setTextFill(Color.RED);
            errorMessage.setText("Title cannot be empty");
            return;
        }
        // May be unnecessary
        if (description.isEmpty()) {
            errorMessage.setTextFill(Color.RED);
            errorMessage.setText("Description cannot be empty");
            return;
        }

        LocalDateTime startTime = currentDate.withHour(startTimeHourSpinnerValue).withMinute(startTimeMinuteSpinnerValue);
        LocalDateTime endTime = currentDate.withHour(endTimeHourSpinnerValue).withMinute(endTimeMinuteSpinnerValue);

        if (startTime.isAfter(endTime)) {
            errorMessage.setTextFill(Color.RED);
            errorMessage.setText("Start time cannot be after end time");
            return;
        }

        if (currentEvent == null) {
            calenderService.createEvent(title, description, startTime, endTime);
        } else {
            calenderService.editEvent(currentEvent, title, description, startTime, endTime);
        }

        closeWindowAction(event);
    }

    @FXML
    public void closeWindowAction(ActionEvent event) {
        Stage stage = (Stage) titleField.getScene().getWindow();
        stage.close();

    AccountService.update();

    closeWindowAction(event);
  }

  @FXML
  public void deleteButton(ActionEvent event) {
    calenderService.deleteEvent(currentEvent);
    AccountService.update();
    closeWindowAction(event);
  }

  public void setScene() {
      if (currentEvent != null) {
          titleField.setText(currentEvent.getTitle());
          descriptionField.setText(currentEvent.getDescription());
          SpinnerValueFactory<Integer> valueFactory1 = startTimeHourSpinner.getValueFactory();
          valueFactory1.setValue(currentEvent.getStartDate().getHour());
          SpinnerValueFactory<Integer> valueFactory2 = startTimeMinuteSpinner.getValueFactory();
          valueFactory2.setValue(currentEvent.getStartDate().getMinute());
          SpinnerValueFactory<Integer> valueFactory3 = endTimeHourSpinner.getValueFactory();
          valueFactory3.setValue(currentEvent.getEndDate().getHour());
          SpinnerValueFactory<Integer> valueFactory4 = endTimeMinuteSpinner.getValueFactory();
          valueFactory4.setValue(currentEvent.getEndDate().getMinute());
      }
  }
}
