package com.grupp5.agila_schemalggare.controllers;

import com.grupp5.agila_schemalggare.services.CalendarService;
import com.grupp5.agila_schemalggare.utils.DynamicController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.time.LocalDateTime;

public class CalendarMonthController implements DynamicController {
  @FXML
  private Button previousMonthButton;
  @FXML
  private Label monthLabel;
  @FXML
  private Button nextMonthButton;
  @FXML
  private Label timeSpanLabel;
  @FXML
  private GridPane gridPane;

  private final CalendarService calendarService = new CalendarService();
  private LocalDateTime currentDate;

  @FXML
  public void openDayAction(ActionEvent event) {
    Button button = (Button) event.getSource();
    int day = Integer.parseInt(button.getText().split(" ")[0]);
    LocalDateTime chosenDay = currentDate.withDayOfMonth(day);

    calendarService.openDayView(chosenDay);
  }

  @FXML
  public void switchToPreviousMonth(ActionEvent event) {
    currentDate = currentDate.minusMonths(1);
    updateView();
  }

  @FXML
  public void switchToNextMonth(ActionEvent event) {
    currentDate = currentDate.plusMonths(1);
    updateView();
  }

  protected void setMonthLabel() {
    this.monthLabel.setText(currentDate.getMonth().toString());
  }

  protected void setTimeSpanLabel() {
    LocalDateTime startDate = currentDate.withDayOfMonth(1);
    LocalDateTime endDate = currentDate.withDayOfMonth(1).plusMonths(1).minusDays(1);

    timeSpanLabel.setText(startDate.toLocalDate() + "  |  " + endDate.toLocalDate());
  }

  private void fillGrid() {
    addDaysToGrid();

    int weekDay = currentDate.withDayOfMonth(1).getDayOfWeek().getValue();
    int day = 1;
    int maxDay = currentDate.withDayOfMonth(currentDate.withDayOfMonth(1).plusMonths(1).minusDays(1).getDayOfMonth()).getDayOfMonth();
    LocalDateTime lastMonth = currentDate.minusMonths(1);
    int lastMonthDays = lastMonth.withDayOfMonth(lastMonth.withDayOfMonth(1).plusMonths(1).minusDays(1).getDayOfMonth()).getDayOfMonth() - weekDay + 1;
    int nextMonthDays = 1;
    int rowHeight = -1;
    int lastColIndex = 0;

    for (int col = 0; col < gridPane.getColumnCount(); col++) {
      if (--weekDay > 0) {
        Label label = new Label(String.valueOf(++lastMonthDays));
        addLabelToGrid(label, 1, col);
        continue;
      }
      addButtonToGrid(String.valueOf(day++), 1, col);
    }

    outer:
    for (int row = 2; row < gridPane.getRowCount(); row++) {
      for (int col = 0; col < gridPane.getColumnCount(); col++) {
        addButtonToGrid(String.valueOf(day++), row, col);
        if (day > maxDay) {
          rowHeight = row;
          lastColIndex = col+1;
          break outer;
        }
      }
    }

    for (int col = lastColIndex; col < gridPane.getColumnCount(); col++) {
      Label label = new Label(String.valueOf(nextMonthDays++));
      addLabelToGrid(label, rowHeight, col);
    }
  }

  private void addDaysToGrid() {
    gridPane.add(createLabel("Monday"), 0, 0);
    gridPane.add(createLabel("Tuesday"), 1, 0);
    gridPane.add(createLabel("Wednesday"), 2, 0);
    gridPane.add(createLabel("Thursday"), 3, 0);
    gridPane.add(createLabel("Friday"), 4, 0);
    gridPane.add(createLabel("Saturday"), 5, 0);
    gridPane.add(createLabel("Sunday"), 6, 0);
  }

  private Label createLabel(String name) {
    Label label = new Label(name);
    label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    label.setAlignment(Pos.BOTTOM_CENTER);
    return label;
  }

  private void addLabelToGrid(Label label, int row, int col) {
    label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    label.setAlignment(Pos.CENTER);
    label.setDisable(true);
    gridPane.add(label, col, row);
  }

  private void addButtonToGrid(String text, int row, int col) {
      int day = Integer.parseInt(text);
      LocalDateTime dayDate = currentDate.withDayOfMonth(day);

      var events = calendarService.getSpecificEvent(dayDate);

      // Lägger till en siffra beroende på hur många och om det finns events för det datumet.
      String buttonText = text;
      if (!events.isEmpty()) {
          buttonText += " (" + events.size() + ")";
      }

      Button button = new Button(buttonText);
      button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
      button.setStyle("-fx-cursor: pointer;");
      button.setOnAction(this::openDayAction);

      // Om vi vill ha färg för att highlighta?
//      if (!events.isEmpty()) {
//          button.setStyle("-fx-background-color: lightblue; -fx-border-color: darkblue;");
//      }

    gridPane.add(button, col, row);
  }

  @Override
  public void updateView() {
    gridPane.getChildren().clear();
    setMonthLabel();
    setTimeSpanLabel();

    fillGrid();
  }

  @Override
  public void setCurrentDate(LocalDateTime currentDate) {
    this.currentDate = currentDate;
  }
}
