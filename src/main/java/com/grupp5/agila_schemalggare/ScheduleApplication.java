package com.grupp5.agila_schemalggare;

import com.grupp5.agila_schemalggare.utils.SceneManagerProvider;
import com.grupp5.agila_schemalggare.utils.SceneManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class ScheduleApplication extends Application {
  @Override
  public void start(Stage stage) throws IOException {
      SceneManager sceneManager = new SceneManager(stage);
      SceneManagerProvider.setSceneManager(sceneManager);

      FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/grupp5/agila_schemalggare/login-view.fxml"));
      Parent root = loader.load();
      stage.setScene(new Scene(root, 800, 500));
      stage.setTitle("Schedule Application");
      stage.show();
  }
}
