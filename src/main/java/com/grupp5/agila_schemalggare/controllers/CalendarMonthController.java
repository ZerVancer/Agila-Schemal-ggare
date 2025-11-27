package com.grupp5.agila_schemalggare.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CalendarMonthController implements Initializable {
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

  // TEMP:
  private LocalDate date = LocalDate.now();

  // Future button use
  @FXML
  public void buttonAction(ActionEvent event) {
    Button button = (Button) event.getSource();
  }

  @FXML
  public void switchToPreviousMonth(ActionEvent event) {
    date = date.minusMonths(1);
    initialize(null, null);
  }

  @FXML
  public void switchToNextMonth(ActionEvent event) {
    date = date.plusMonths(1);
    initialize(null, null);
  }

  protected void setMonthLabel() {
    this.monthLabel.setText(date.getMonth().toString());
  }

  protected void setTimeSpanLabel() {
    LocalDate startDate = date.withDayOfMonth(1);
    LocalDate endDate = date.withDayOfMonth(date.lengthOfMonth());

    timeSpanLabel.setText(startDate + "  |  " + endDate);
  }

  private void fillGrid() {
    addDaysToGrid();

    int weekDay = date.withDayOfMonth(1).getDayOfWeek().getValue();
    int day = 1;
    int maxDay = date.withDayOfMonth(date.lengthOfMonth()).getDayOfMonth();
    LocalDate lastMonth = date.minusMonths(1);
    int lastMonthDays = lastMonth.withDayOfMonth(lastMonth.lengthOfMonth()).getDayOfMonth() - weekDay + 1;
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
    Button button = new Button(String.valueOf(text));
    button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    button.setOnAction(this::buttonAction);
    gridPane.add(button, col, row);
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    gridPane.getChildren().clear();
    setMonthLabel();
    setTimeSpanLabel();

    fillGrid();
  }

}
