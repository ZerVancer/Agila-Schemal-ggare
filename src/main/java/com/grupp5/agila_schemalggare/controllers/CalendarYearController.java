package com.grupp5.agila_schemalggare.controllers;

import com.grupp5.agila_schemalggare.services.CalendarService;

import com.grupp5.agila_schemalggare.utils.DynamicController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CalendarYearController implements DynamicController {
    @FXML
    private Button prevYearButton;
    @FXML
    private Label yearLabel;
    @FXML
    private Button nextYearButton;
    @FXML
    private Label monthsLabel;
    @FXML
    private GridPane yearGrid;

    private LocalDateTime currentDate;
    private final CalendarService calendarService = new CalendarService();
    private CalendarViewController calendarViewController;

    @Override
    public void setCurrentDate(LocalDateTime currentDate) {
      this.currentDate = currentDate;
    }

    @FXML
    public void switchToPrevYear(ActionEvent event) {
        currentDate = currentDate.minusYears(1);
        updateView();
    }

    @FXML
    public void switchToNextYear(ActionEvent event) {
        currentDate = currentDate.plusYears(1);
        updateView();
    }

    public void setCalendarViewController(CalendarViewController controller) {
      this.calendarViewController = controller;
    }

    protected void setYearLabel() {
        yearLabel.setText(String.valueOf(currentDate.getYear()));
    }

    protected void setMonthsLabel() {
        LocalDateTime startDate = currentDate.withDayOfYear(1);
        LocalDateTime endDate = currentDate.withDayOfYear(currentDate.toLocalDate().lengthOfYear());

        monthsLabel.setText(startDate.toLocalDate() + " | " + endDate.toLocalDate());
    }

    private void createYear() {
        yearGrid.getChildren().clear();

        LocalDateTime startMonth = currentDate.minusYears(1).withMonth(7).withDayOfMonth(1);

        int col = 0;
        int row = 0;

        DateTimeFormatter monthFormat = DateTimeFormatter.ofPattern("MMM");

        var allEvents = calendarService.getAllEvents();

        for (int i = 0; i < 24; i++) {
            LocalDateTime monthDate = startMonth.plusMonths(i);

            long eventCount = allEvents.stream()
                    .filter(event ->
                            event.getStartDate().getYear() == monthDate.getYear() &&
                                    event.getStartDate().getMonthValue() == monthDate.getMonthValue())
                    .count();

            String monthText = monthDate.format(monthFormat);

            String buttonText = (eventCount > 0) ?
                    monthText + " " + monthDate.getYear() + " (" + eventCount + ")"
                    : monthText + " " + monthDate.getYear();

            Button monthButton = new Button(buttonText);
            monthButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

            if (monthDate.getYear() != currentDate.getYear()) {
                monthButton.setStyle("-fx-background-color: lightgray;");
                monthButton.setDisable(true);
            }

            monthButton.setOnAction(event -> calendarViewController.showMonthViewWithDate(monthDate));

            yearGrid.add(monthButton, col, row);

            col++;
            if (col >= 6) {
                col = 0;
                row++;
            }
        }
    }

    @Override
    public void updateView() {
      setYearLabel();
      setMonthsLabel();
      createYear();
    }

}
