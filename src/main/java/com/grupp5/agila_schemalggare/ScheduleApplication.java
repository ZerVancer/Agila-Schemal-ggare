package com.grupp5.agila_schemalggare;

import com.grupp5.agila_schemalggare.controllers.LoginController;
import com.grupp5.agila_schemalggare.services.AccountService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ScheduleApplication extends Application {
  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(ScheduleApplication.class.getResource("login-view.fxml"));

    Scene scene = new Scene(fxmlLoader.load(), 320, 240);
    stage.setTitle("Hello!");
    stage.setScene(scene);

      LoginController loginController = fxmlLoader.getController();
      loginController.setAccountService(new AccountService());

    stage.show();
  }
}
