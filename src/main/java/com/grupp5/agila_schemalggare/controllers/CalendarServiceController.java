package com.grupp5.agila_schemalggare.controllers;

import com.grupp5.agila_schemalggare.models.Event;
import com.grupp5.agila_schemalggare.services.AccountService;
import com.grupp5.agila_schemalggare.utils.SceneManager;
import com.grupp5.agila_schemalggare.utils.ServiceRegister;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class CalendarServiceController implements Initializable, ServiceRegister {
  @FXML
  protected Label label;
  @FXML
  protected Button createButton;
  @FXML
  protected Button editButton;
  @FXML
  protected ChoiceBox<Event> choiceBox;

  AccountService accountService;
  SceneManager sceneManager;

  @FXML
  protected void createButtonAction(ActionEvent event) {
    //accountService.nullCurrentEvent();
    switchSceneToCreateEdit();
  }

  @FXML
  protected void editButtonAction(ActionEvent event) {
    Event eventToEdit = choiceBox.getSelectionModel().getSelectedItem();
    //accountService.setCurrentEvent(eventToEdit);
    switchSceneToCreateEdit();
  }

  private void switchSceneToCreateEdit() {
    sceneManager = new SceneManager((Stage) label.getScene().getWindow(), accountService);
    sceneManager.switchScene("/com/grupp5/agila_schemalggare/eventCreatorService.fxml");
  }

  private void fillBoxWithEvents() {
    ObservableList<Event> items = FXCollections.observableArrayList(AccountService.getLoggedInAccount().getCalendar().getEvents());
    choiceBox.setItems(items);
    choiceBox.getSelectionModel().select(0);
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    fillBoxWithEvents();
  }

  @Override
  public void registerAccountService(AccountService accountService) {
    this.accountService = accountService;
  }
}
