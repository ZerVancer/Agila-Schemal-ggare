package com.grupp5.agila_schemalggare.controllers;

import com.grupp5.agila_schemalggare.services.CalendarService;
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

  private final CalendarService calendarService = new CalendarService();

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

  // Arbetar utifrån hur du strukturerade det hela - Joel
  @FXML
  public Button mondayButton;
  @FXML
  public Button tuesdayButton;
  @FXML
  public Button wednesdayButton;
  @FXML
  public Button thursdayButton;
  @FXML
  public Button fridayButton;
  @FXML
  public Button saturdayButton;
  @FXML
  public Button sundayButton;

  private Button[] dayButtons;
  private Label[] dayLabels;

  private LocalDate[] weekDates;

  // - Joel

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

    // Uppdaterade och tog bort offset namnet för tydlighet.
    // Märkte även att den gjorde inget för det andra datumet dvs endDate.
  public void setTimeSpanLabel() {
      int dayOfWeek = date.getDayOfWeek().getValue();

      LocalDate startDate = date.minusDays(dayOfWeek - 1);
      LocalDate endDate = date.plusDays(7 - dayOfWeek);

      timeSpanLabel.setText(startDate + "  |  " + endDate);
  }

  public void setDayDate() {
    weekDates = new LocalDate[7];
    int dayOfWeek = date.getDayOfWeek().getValue();

    LocalDate monday = date.minusDays(dayOfWeek - 1);

    for (int i = 0; i < 7; i++) {
        weekDates[i] = monday.plusDays(i);
        dayLabels[i].setText(weekDates[i].getDayOfWeek() + " " + weekDates[i].getDayOfMonth());
    }
  }

  public void renderEvents() {
      for (int i = 0; i < weekDates.length; i++) {
          LocalDate day = weekDates[i];

          var events = calendarService.getSpecificEvent(day);

          StringBuilder stringBuilder = new StringBuilder(dayButtons[i].getText());

          for (var event : events) {
              String startTime = String.format("%02d:%02d", event.getStartDate().getHour(), event.getStartDate().getMinute());
              String endTime = String.format("%02d:%02d", event.getEndDate().getHour(), event.getEndDate().getMinute());

              stringBuilder.append("| ")
                      .append(event.getTitle())
                      .append(" |\n")
                      .append("| ")
                      .append(startTime)
                      .append(" - ")
                      .append(endTime)
                      .append(" |\n");
          }

          dayButtons[i].setText(stringBuilder.toString());


          if (!events.isEmpty()) {
              dayButtons[i].setStyle("-fx-border-color: lightblue;");
          } else {
              dayButtons[i].setStyle("");
          }
      }
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    dayButtons = new Button[]{mondayButton, tuesdayButton, wednesdayButton, thursdayButton, fridayButton, saturdayButton, sundayButton};
    dayLabels = new Label[]{mondayLabel, tuesdayLabel, wednesdayLabel, thursdayLabel, fridayLabel, saturdayLabel, sundayLabel};

    setWeekLabel();
    setTimeSpanLabel();
    setDayDate();
    renderEvents();
  }
}
