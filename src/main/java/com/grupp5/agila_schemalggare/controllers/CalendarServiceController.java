package com.grupp5.agila_schemalggare.controllers;

import com.grupp5.agila_schemalggare.models.Event;
import com.grupp5.agila_schemalggare.services.AccountService;
import com.grupp5.agila_schemalggare.services.CalendarService;
import com.grupp5.agila_schemalggare.utils.SceneManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import java.io.IOException;
import java.time.LocalDateTime;

public class CalendarServiceController {
  @FXML
  protected Label label;
  @FXML
  protected Button createButton;
  @FXML
  protected Button editButton;
  @FXML
  protected ChoiceBox<Event> choiceBox;

  private final CalendarService calendarService = new  CalendarService();
  private LocalDateTime currentDate;

  public void setCurrentDate(LocalDateTime currentDate) {
    this.currentDate = currentDate;
  }

  @FXML
  protected void createButtonAction(ActionEvent event) {
    switchSceneToCreateEdit(null);
  }

  @FXML
  protected void editButtonAction(ActionEvent event) {
    Event eventToEdit = choiceBox.getSelectionModel().getSelectedItem();
    switchSceneToCreateEdit(eventToEdit);
  }

  private void switchSceneToCreateEdit(Event eventToEdit) {
    try {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/grupp5/agila_schemalggare/eventService.fxml"));
      Parent root = loader.load();
      EventServiceController controller = loader.getController();
      controller.setCurrentDate(currentDate);
      controller.setCurrentEvent(eventToEdit);
      controller.setScene();
      Scene scene = label.getScene();
      scene.setRoot(root);
    } catch (IOException e) {
      throw new RuntimeException("File not found");
    }
  }

  private void fillBoxWithEvents() {
    ObservableList<Event> items = FXCollections.observableArrayList(calendarService.getSpecificEvent(currentDate));
    choiceBox.setItems(items);
    choiceBox.getSelectionModel().select(0);
  }

  public void setScene() {
    fillBoxWithEvents();
  }

}
