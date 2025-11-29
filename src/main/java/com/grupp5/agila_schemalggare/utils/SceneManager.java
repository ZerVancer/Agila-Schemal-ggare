package com.grupp5.agila_schemalggare.utils;

import com.grupp5.agila_schemalggare.services.AccountService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class SceneManager {
    private final Stage stage;
    private final AccountService accountService;

    public SceneManager(Stage stage, AccountService accountService) {
        this.stage = stage;
        this.accountService = accountService;
    }

    public void switchScene(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Object controller = loader.getController();
            if(controller instanceof ServiceRegister) {
                ((ServiceRegister) controller).registerAccountService(accountService);
            }

            if (stage.getScene() == null) {
                stage.setScene(new javafx.scene.Scene(root, 700, 400));
            } else {
                stage.getScene().setRoot(root);
            }


        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
