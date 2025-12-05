package com.grupp5.agila_schemalggare.controllers;

import com.grupp5.agila_schemalggare.services.AccountService;
import com.grupp5.agila_schemalggare.utils.DynamicController;
import com.grupp5.agila_schemalggare.utils.SceneManager;
import com.grupp5.agila_schemalggare.utils.SceneManagerProvider;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.time.LocalDateTime;

public class CalendarViewController implements DynamicController {
    @FXML
    private StackPane viewRender;
    @FXML
    private VBox sideMenu;

    private Parent weekView;
    private CalendarWeekController calendarWeekController;

    private Parent monthView;
    private CalendarMonthController calendarMonthController;

    private Parent yearView;
    private CalendarYearController calendarYearController;

    private SideMenuController sideMenuController;

    private final SceneManager sceneManager = SceneManagerProvider.getSceneManager();
    private LocalDateTime currentDate;

    public void showWeekView() {
        viewRender.getChildren().setAll(weekView);
    }

    public void showMonthView() {
        viewRender.getChildren().setAll(monthView);
    }

    // Inför year, om vi har tid.
    public void showYearView() {
      viewRender.getChildren().setAll(yearView);
    }

    public void showMonthViewWithDate(LocalDateTime date) {
      calendarMonthController.setCurrentDate(date);
      calendarMonthController.updateView();
      sideMenuController.handleMonthlyClick();
    }

    // Vid start
    @Override
    public void updateView() {
        // Försökte få ihop det med SceneManager, problemet är att den just nu påverkar hela scenen.
        // Därav får FXMLLoader ordna detta.


        FXMLLoader weekLoader = sceneManager.getFXMLLoader("/com/grupp5/agila_schemalggare/calendarWeek.fxml", currentDate);
        calendarWeekController = weekLoader.getController();
        weekView = weekLoader.getRoot();

        FXMLLoader monthLoader = sceneManager.getFXMLLoader("/com/grupp5/agila_schemalggare/calendarMonth.fxml", currentDate);
        calendarMonthController = monthLoader.getController();
        monthView = monthLoader.getRoot();

        FXMLLoader yearLoader = sceneManager.getFXMLLoader("/com/grupp5/agila_schemalggare/calendarYear.fxml", currentDate);
        calendarYearController = yearLoader.getController();
        yearView = yearLoader.getRoot();
        calendarYearController.setCalendarViewController(this);

        showYearView();

        FXMLLoader menuLoader = sceneManager.switchScene("/com/grupp5/agila_schemalggare/side-menu.fxml");
        sideMenuController = menuLoader.getController();
        VBox sideMenuNode = menuLoader.getRoot();
        sideMenuController.setCalendarViewController(this);

        AccountService.addUpdator(calendarWeekController);
        AccountService.addUpdator(calendarMonthController);
        AccountService.addUpdator(calendarYearController);
        AccountService.addUpdator(sideMenuController);

        sideMenu.getChildren().setAll(sideMenuNode);
    }

    @Override
    public void setCurrentDate(LocalDateTime currentDate) {
        this.currentDate = currentDate;
    }
}
