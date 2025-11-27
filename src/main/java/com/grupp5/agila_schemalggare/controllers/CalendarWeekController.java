package com.grupp5.agila_schemalggare.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.ResourceBundle;

public class CalendarWeekController implements Initializable {
  @FXML
  public Button previousWeekButton;
  @FXML
  private Label weekLabel;
  @FXML
  public Button nextWeekButton;
  @FXML
  public Label timeSpanLabel;
  @FXML
  public Label mondayLabel;
  @FXML
  public Label tuesdayLabel;
  @FXML
  public Label wednesdayLabel;
  @FXML
  public Label thursdayLabel;
  @FXML
  public Label fridayLabel;
  @FXML
  public Label saturdayLabel;
  @FXML
  public Label sundayLabel;

  // TEMP:
  private LocalDate date = LocalDate.now();

  // Future button use
  @FXML
  protected void buttonAction(ActionEvent event) {
  }

  @FXML
  protected void switchToPreviousWeek() {
    date = date.minusWeeks(1);
    initialize(null, null);
  }
  @FXML
  protected void switchToNextWeek() {
    date = date.plusWeeks(1);
    initialize(null, null);
  }

  public void setWeekLabel() {
    weekLabel.setText("Week " + date.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear()));
  }

  public void setTimeSpanLabel() {
    int offset = date.getDayOfWeek().getValue();

    LocalDate startDate = date.minusDays(--offset);
    LocalDate endDate = date.plusDays(offset);

    timeSpanLabel.setText(startDate + "  |  " + endDate);
  }

  public void setDayDate() {
    int offset = date.getDayOfWeek().getValue();

    mondayLabel.setText("Monday " + (date.getDayOfMonth()-(--offset)));
    tuesdayLabel.setText("Tuesday " + (date.getDayOfMonth()-(--offset)));
    wednesdayLabel.setText("Wednesday " + (date.getDayOfMonth()-(--offset)));
    thursdayLabel.setText("Thursday " + (date.getDayOfMonth()-(--offset)));
    fridayLabel.setText("Friday " + (date.getDayOfMonth()-(--offset)));
    saturdayLabel.setText("Saturday " + (date.getDayOfMonth()-(--offset)));
    sundayLabel.setText("Sunday " + (date.getDayOfMonth()-(--offset)));
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    setWeekLabel();
    setTimeSpanLabel();
    setDayDate();
  }
}
