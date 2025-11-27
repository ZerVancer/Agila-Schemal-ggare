package com.grupp5.agila_schemalggare;

import com.grupp5.agila_schemalggare.services.AccountService;
import com.grupp5.agila_schemalggare.utils.SceneManagerProvider;
import com.grupp5.agila_schemalggare.utils.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;

public class ScheduleApplication extends Application {
  @Override
  public void start(Stage stage) throws IOException {
      AccountService accountService = new AccountService();
      SceneManager sceneManager = new SceneManager(stage, accountService);

      SceneManagerProvider.setSceneManager(sceneManager);

      sceneManager.switchScene("/com/grupp5/agila_schemalggare/login-view.fxml");

      stage.setTitle("Schedule Application");
      stage.show();
  }
}
