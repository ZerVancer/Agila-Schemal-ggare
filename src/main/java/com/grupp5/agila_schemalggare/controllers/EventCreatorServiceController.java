package com.grupp5.agila_schemalggare.controllers;

import com.grupp5.agila_schemalggare.models.Account;
import com.grupp5.agila_schemalggare.models.EventCreatorService;
import com.grupp5.agila_schemalggare.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDateTime;

public class EventCreatorServiceController {
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
      errorMessage.setText("Title cannot be empty");
      return;
    }
    // May be unnecessary
    if (description.isEmpty()) {
      errorMessage.setText("Description cannot be empty");
      return;
    }

    // Converts chosen time to LocalDateTime
    // WARNING: Uses .now() which is a temporary method before the calendar is dynamic
    LocalDateTime startTime = LocalDateTime.now().withHour(startTimeHourSpinnerValue).withMinute(startTimeMinuteSpinnerValue);
    LocalDateTime endTime = LocalDateTime.now().withHour(endTimeHourSpinnerValue).withMinute(endTimeMinuteSpinnerValue);

    if (startTime.isAfter(endTime)) {
      errorMessage.setText("Start time cannot be after end time");
      return;
    }

    // WARNING: Hardcoded user
    Account account = new User("t", "Test");

    EventCreatorService calenderService = new EventCreatorService();
    calenderService.createEvent(account, title, description, startTime, endTime);

    closeWindowAction(event);
  }

  @FXML
  public void closeWindowAction(ActionEvent event) {
    Stage stage = (Stage) cancelButton.getScene().getWindow();
    stage.close();
  }

}
