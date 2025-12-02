package com.grupp5.agila_schemalggare.controllers;

import com.grupp5.agila_schemalggare.services.CalendarService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CalendarYearController {
    private final CalendarService calendarService = new CalendarService();

    private CalendarViewController calendarViewController;

    public void setCalendarViewController(CalendarViewController controller) {
        this.calendarViewController = controller;
    }

    private LocalDate clickedMonth;

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

    private LocalDateTime year = LocalDateTime.now();

    private Parent monthView;
    private CalendarMonthController calendarMonthController;

    @FXML
    public void buttonAction(ActionEvent event, LocalDate month) {
        Button button = (Button) event.getSource();

        button.getUserData();

        System.out.println();
    }

    @FXML
    public void switchToPrevYear(ActionEvent event) {
        year = year.minusYears(1);
        initialize();
    }

    @FXML
    public void switchToNextYear(ActionEvent event) {
        year = year.plusYears(1);
        initialize();
    }

    protected void setYearLabel() {
        yearLabel.setText(String.valueOf(year.getYear()));
    }

    protected void setMonthsLabel() {
        LocalDateTime startDate = year.withDayOfYear(1);
        LocalDateTime endDate = year.withDayOfYear(year.toLocalDate().lengthOfYear());

        monthsLabel.setText(startDate.toLocalDate() + " | " + endDate.toLocalDate());
    }

    private void createYear() {
        yearGrid.getChildren().clear();

        LocalDateTime startMonth = year.minusYears(1).withMonth(7).withDayOfMonth(1);

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
            monthButton.setPrefWidth(100);
            monthButton.setPrefHeight(80);

            if (monthDate.getYear() != year.getYear()) {
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

    private void updateYearView() {
        setYearLabel();
        setMonthsLabel();
        createYear();
    }

    public void initialize() {
        updateYearView();
    }

}
