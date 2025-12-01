package com.grupp5.agila_schemalggare.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class CalendarViewController {
    @FXML
    private StackPane viewRender;

    @FXML
    private VBox sideMenu;

    private Parent weekView;
    private CalendarWeekController calendarWeekController;

    private Parent monthView;
    private CalendarMonthController calendarMonthController;

    // TODO: Inför year controllern om vi gör det i tid.
//    private Parent yearView;
//    private CalendarYearController calendarYearController;

    public void showWeekView() {
        viewRender.getChildren().setAll(weekView);
    }

    public void showMonthView() {
        viewRender.getChildren().setAll(monthView);
    }

    // Inför year, om vi har tid.
    public void showYearView() {
        // Fyll ut här när Year-view finns.
    }

    // Vid start
    public void initialize() throws IOException {
        // Försökte få ihop det med SceneManager, problemet är att den just nu påverkar hela scenen.
        // Därav får FXMLLoader ordna detta.

         FXMLLoader weekLoader = new FXMLLoader(getClass().getResource("/com/grupp5/agila_schemalggare/calendarWeek.fxml"));
         weekView = weekLoader.load();
         calendarWeekController = weekLoader.getController();

        FXMLLoader monthLoader = new FXMLLoader(getClass().getResource("/com/grupp5/agila_schemalggare/calendarMonth.fxml"));
        monthView = monthLoader.load();
        calendarMonthController = monthLoader.getController();

        // Inför year.
//        FXMLLoader yearLoader = new FXMLLoader(getClass().getResource("/com/grupp5/agila_schemalggare/<Fyll ut här>.fxml"));
//        yearView = yearLoader.load();
//        calendarYearController = yearLoader.getController();

        viewRender.getChildren().setAll(monthView);



        FXMLLoader sideLoader = new FXMLLoader(getClass().getResource("/com/grupp5/agila_schemalggare/side-menu.fxml"));
        VBox sideMenuNode = sideLoader.load();
        SideMenuController sideMenuController = sideLoader.getController();
        sideMenuController.setCalendarViewController(this);

        sideMenu.getChildren().setAll(sideMenuNode);
    }


}
