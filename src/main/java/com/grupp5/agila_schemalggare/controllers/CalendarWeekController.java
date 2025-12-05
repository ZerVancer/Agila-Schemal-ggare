package com.grupp5.agila_schemalggare.controllers;

import com.grupp5.agila_schemalggare.models.Event;
import com.grupp5.agila_schemalggare.services.CalendarService;
import com.grupp5.agila_schemalggare.utils.DynamicController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class CalendarWeekController implements DynamicController {

    @FXML
    private GridPane weekGrid;
    @FXML
    public Button previousWeekButton;
    @FXML
    private Label weekLabel;
    @FXML
    public Button nextWeekButton;
    @FXML
    public Label timeSpanLabel;

    private LocalDateTime[] weekDates;
    // - Joel

    private final CalendarService calendarService = new CalendarService();
    private LocalDateTime currentDate;

    // Future button use
    @FXML
    protected void openDayAction(ActionEvent event) {
        Button button = (Button) event.getSource();
        String dayString = button.getText().split("-")[2];
        if (dayString.charAt(0) == '0') dayString = String.valueOf(dayString.charAt(1));
        int day = Integer.parseInt(dayString);
        calendarService.openDayView(currentDate.withDayOfMonth(day));
    }

    @FXML
    protected void switchToPreviousWeek() {
        currentDate = currentDate.minusWeeks(1);
        updateView();
    }

    @FXML
    protected void switchToNextWeek() {
        currentDate = currentDate.plusWeeks(1);
        updateView();
    }

    public void setWeekLabel() {
        weekLabel.setText("Week " + currentDate.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear()));
    }

    // Uppdaterade och tog bort offset namnet för tydlighet.
    // Märkte även att den gjorde inget för det andra datumet dvs endDate.
    public void setTimeSpanLabel() {
        int dayOfWeek = currentDate.getDayOfWeek().getValue();

        LocalDateTime startDate = currentDate.minusDays(dayOfWeek - 1);
        LocalDateTime endDate = currentDate.plusDays(7 - dayOfWeek);

        timeSpanLabel.setText(startDate.toLocalDate() + "  |  " + endDate.toLocalDate());
    }

    public void setDayDate() {
        weekDates = new LocalDateTime[7];
        int dayOfWeek = currentDate.getDayOfWeek().getValue();

        LocalDateTime monday = currentDate.minusDays(dayOfWeek - 1);

        DateTimeFormatter dayFormat = DateTimeFormatter.ofPattern("EEEE MMM");

        for (int i = 0; i < 7; i++) {
            LocalDateTime current = monday.plusDays(i);

            weekDates[i] = current;

            String dayName = current.toLocalDate().format(dayFormat);
            int dayNumber = current.getDayOfMonth();

            String labelText = dayName + " " + dayNumber;
            Label label = new Label(labelText);
            label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            label.setAlignment(Pos.CENTER);
            weekGrid.add(label, i, 0);
        }
    }

    public void renderEvents() {
        weekGrid.getChildren().clear();
        for (int i = 0; i < weekDates.length; i++) {
            LocalDateTime day = weekDates[i];

            var events = calendarService.getSpecificEvent(day);

            StringBuilder stringBuilder = new StringBuilder(day.toLocalDate().toString() + "\n");
            int counter = 0;

            for (int j = 0; j < events.size(); j++) {
                Event event = events.get(j);

                if (j < 3) { //show max 3 events

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
                } else {
                    counter++;
                }
            }

            if (counter > 0) {
                stringBuilder.append("and ")
                        .append(counter)
                        .append(" more...");
            }

            Button button = new Button(weekDates[i].getDayOfWeek().toString());
            button.setOnAction(this::openDayAction);
            button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            button.setAlignment(Pos.CENTER);
            button.setText(stringBuilder.toString());

            weekGrid.add(button, i, 1);

            if (!events.isEmpty()) {
                button.setStyle("-fx-border-color: lightblue;");
            } else {
                button.setStyle("");
            }

            setDayDate();

        }
    }

    @Override
    public void updateView() {
        setWeekLabel();
        setTimeSpanLabel();
        setDayDate();
        renderEvents();
    }

  @Override
  public void setCurrentDate(LocalDateTime currentDate) {
    this.currentDate = currentDate;
  }
}
