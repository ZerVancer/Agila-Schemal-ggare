package com.grupp5.agila_schemalggare.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;

public class SceneManager {
    private final Stage stage;

    public SceneManager(Stage stage) {
        this.stage = stage;
    }

    public FXMLLoader getFXMLLoader(String fxmlPath, LocalDateTime currentDate) {
      try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        loader.load();

        setCurrentDateOnController(loader, currentDate);

        return loader;
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

    public FXMLLoader switchScene(String fxmlPath, LocalDateTime currentDate) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            setCurrentDateOnController(loader, currentDate);

            stage.getScene().setRoot(root);

            return loader;
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public FXMLLoader switchScene(String fxmlPath) {
      try {
        return switchScene(fxmlPath, null);
      }
      catch (Exception exception) {
        throw new RuntimeException("Incorrect method used, missing date");
      }
    }

    public FXMLLoader openNewScene(String fxmlPath, LocalDateTime currentDate, String title, double width, double height) {
        try {
          FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
          Parent root = loader.load();

          Object controller;
          if ((controller = loader.getController()) instanceof DynamicController) {
            ((DynamicController) controller).setCurrentDate(currentDate);
            ((DynamicController) controller).updateView();
          }

          Stage stage = new Stage();
          stage.setTitle(title);
          stage.setScene(new Scene(root, width, height));
          stage.show();
          return loader;
        } catch (Exception exception) {
          throw new RuntimeException(exception);
        }
    }

    private void setCurrentDateOnController(FXMLLoader loader, LocalDateTime currentDate) {
      Object controller;
      if ((controller = loader.getController()) instanceof DynamicController) {
        ((DynamicController) controller).setCurrentDate(currentDate);
        ((DynamicController) controller).updateView();
      }
    }
}
