package com.grupp5.agila_schemalggare.controllers;

import com.grupp5.agila_schemalggare.models.Event;
import com.grupp5.agila_schemalggare.services.AccountService;
import com.grupp5.agila_schemalggare.services.CalendarService;
import com.grupp5.agila_schemalggare.utils.ServiceRegister;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class EventServiceController implements Initializable, ServiceRegister {
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
  @FXML
  private Button deleteButton;


  private AccountService accountService;
  // Change to accountService.getCurrentEvent and accountService.getCurrentDate in Initialize
  private Event curretnEvent = new Event("Test", "test", LocalDateTime.now(), LocalDateTime.now().plusHours(1));
  private LocalDateTime currentDate = LocalDateTime.now();
  private final CalendarService calenderService = new CalendarService();

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

    if (curretnEvent == null) {
      calenderService.createEvent(title, description, startTime, endTime);
    } else {
      calenderService.editEvent(curretnEvent, title, description, startTime, endTime);
    }

    closeWindowAction(event);
  }

  @FXML
  public void closeWindowAction(ActionEvent event) {
    Stage stage = (Stage) titleField.getScene().getWindow();
    stage.close();
  }

  @FXML
  public void deleteButton(ActionEvent event) {
    calenderService.deleteEvent(curretnEvent);
    closeWindowAction(event);
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    if (curretnEvent != null) {
      titleField.setText(curretnEvent.getTitle());
      descriptionField.setText(curretnEvent.getDescription());
      SpinnerValueFactory<Integer> valueFactory1 = startTimeHourSpinner.getValueFactory();
      valueFactory1.setValue(curretnEvent.getStartDate().getHour());
      SpinnerValueFactory<Integer> valueFactory2 = startTimeMinuteSpinner.getValueFactory();
      valueFactory2.setValue(curretnEvent.getStartDate().getMinute());
      SpinnerValueFactory<Integer> valueFactory3 = endTimeHourSpinner.getValueFactory();
      valueFactory3.setValue(curretnEvent.getEndDate().getHour());
      SpinnerValueFactory<Integer> valueFactory4 = endTimeMinuteSpinner.getValueFactory();
      valueFactory4.setValue(curretnEvent.getEndDate().getMinute());
      deleteButton.setVisible(true);
    }
  }

  @Override
  public void registerAccountService(AccountService accountService) {
    this.accountService = accountService;
  }
}
