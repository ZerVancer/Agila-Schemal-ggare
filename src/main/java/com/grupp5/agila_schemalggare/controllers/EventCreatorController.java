package com.grupp5.agila_schemalggare.controllers;

import com.grupp5.agila_schemalggare.models.Account;
import com.grupp5.agila_schemalggare.services.CalendarService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.time.LocalDateTime;

public class EventCreatorController {
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
  private Button cancelButton;
  @FXML
  private Label errorMessage;

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

    // Converts chosen time to LocalDateTime
    // WARNING: Uses .now() which is a temporary method before the calendar is dynamic
    LocalDateTime startTime = LocalDateTime.now().withHour(startTimeHourSpinnerValue).withMinute(startTimeMinuteSpinnerValue);
    LocalDateTime endTime = LocalDateTime.now().withHour(endTimeHourSpinnerValue).withMinute(endTimeMinuteSpinnerValue);

    if (startTime.isAfter(endTime)) {
      errorMessage.setTextFill(Color.RED);
      errorMessage.setText("Start time cannot be after end time");
      return;
    }

    CalendarService calenderService = new CalendarService();
    Account account = calenderService.createEvent(title, description, startTime, endTime);

    if (account != null) {
        errorMessage.setTextFill(Color.GREEN);
        errorMessage.setText("Event created successfully.");
    } else {
        errorMessage.setTextFill(Color.RED);
        errorMessage.setText("Event was not created.");
    }

    closeWindowAction(event);
  }

  @FXML
  public void closeWindowAction(ActionEvent event) {
    Stage stage = (Stage) cancelButton.getScene().getWindow();
    stage.close();
  }

}
