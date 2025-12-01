package com.grupp5.agila_schemalggare.controllers;

import com.grupp5.agila_schemalggare.utils.SceneManagerProvider;
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

    private Parent monthView;
    private CalendarMonthController calendarMonthController;

    private Parent weekView;
    private CalendarWeekController calendarWeekController;

    public void showWeekView() {
        viewRender.getChildren().setAll(weekView);
    }

    public void showMonthView() {
        viewRender.getChildren().setAll(monthView);
    }

    public void showYearView() {
        // Fyll ut här när Year-view finns.
    }

    public void initialize() throws IOException {
        // Försökte få ihop det med SceneManager, problemet är att den just nu påverkar hela scenen.
        // Därav får FXMLLoader ordna detta.
        FXMLLoader monthLoader = new FXMLLoader(getClass().getResource("/com/grupp5/agila_schemalggare/calendarMonth.fxml"));
        monthView = monthLoader.load();
        calendarMonthController = monthLoader.getController();

        FXMLLoader weekLoader = new FXMLLoader(getClass().getResource("/com/grupp5/agila_schemalggare/calendarWeek.fxml"));
        weekView = weekLoader.load();
        calendarWeekController = weekLoader.getController();

        viewRender.getChildren().setAll(monthView);

        FXMLLoader sideLoader = new FXMLLoader(getClass().getResource("/com/grupp5/agila_schemalggare/side-menu.fxml"));
        VBox sideMenuNode = sideLoader.load();
        SideMenuController sideMenuController = sideLoader.getController();
        sideMenuController.setCalendarViewController(this);

        sideMenu.getChildren().setAll(sideMenuNode);
    }


}
