package com.grupp5.agila_schemalggare.controllers;

import com.grupp5.agila_schemalggare.services.CalendarService;
import com.grupp5.agila_schemalggare.utils.Updator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.Arrays;
import java.util.Locale;

public class CalendarWeekController implements Updator {

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

  private LocalDateTime[] weekDates;
  // - Joel

  private final CalendarService calendarService = new CalendarService();
  private LocalDateTime date;

  public void setCurrentDate(LocalDateTime date) {
    this.date = date;
  }

    // Future button use
    @FXML
    protected void buttonAction(ActionEvent event) {
        Button button = (Button) event.getSource();

  @FXML
  protected void switchToPreviousWeek() {
    date = date.minusWeeks(1);
    updateView();
  }
  @FXML
  protected void switchToNextWeek() {
    date = date.plusWeeks(1);
    updateView();
  }

  public void setWeekLabel() {
    weekLabel.setText("Week " + date.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear()));
  }

    // Uppdaterade och tog bort offset namnet för tydlighet.
    // Märkte även att den gjorde inget för det andra datumet dvs endDate.
  public void setTimeSpanLabel() {
      int dayOfWeek = date.getDayOfWeek().getValue();

      LocalDateTime startDate = date.minusDays(dayOfWeek - 1);
      LocalDateTime endDate = date.plusDays(7 - dayOfWeek);

      timeSpanLabel.setText(startDate.toLocalDate() + "  |  " + endDate.toLocalDate());
  }

  public void setDayDate() {
    weekDates = new LocalDateTime[7];
    int dayOfWeek = date.getDayOfWeek().getValue();

    LocalDateTime monday = date.minusDays(dayOfWeek - 1);

    DateTimeFormatter dayFormat = DateTimeFormatter.ofPattern("EEEE MMM");

    for (int i = 0; i < 7; i++) {
        LocalDateTime current = monday.plusDays(i);

        weekDates[i] = current;

        String dayName = current.toLocalDate().format(dayFormat);
        int dayNumber = current.getDayOfMonth();

        String labelText = dayName + " " + dayNumber;
        dayLabels[i].setText(labelText);
    }
  }

    public void renderEvents() {
        for (int i = 0; i < weekDates.length; i++) {
            LocalDateTime day = weekDates[i];

            var events = calendarService.getSpecificEvent(day);

            StringBuilder stringBuilder = new StringBuilder(day.toLocalDate().toString() + "\n");

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
  public void updateView() {
    dayButtons = new Button[]{mondayButton, tuesdayButton, wednesdayButton, thursdayButton, fridayButton, saturdayButton, sundayButton};
    dayLabels = new Label[]{mondayLabel, tuesdayLabel, wednesdayLabel, thursdayLabel, fridayLabel, saturdayLabel, sundayLabel};

        setWeekLabel();
        setTimeSpanLabel();
        setDayDate();
        renderEvents();
    }

    private void openDayView(LocalDateTime date, Button button) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/grupp5/agila_schemalggare/calendarDayView.fxml"));
            Parent root = loader.load();

            CalendarDayController controller = loader.getController();
            controller.setDate(date);

            Stage stage = new Stage();
            stage.setTitle("Day View");
            stage.setScene(new Scene(root, 200, 200));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
