package com.grupp5.agila_schemalggare.controllers;

import com.grupp5.agila_schemalggare.models.Account;
import com.grupp5.agila_schemalggare.services.AccountService;
import com.grupp5.agila_schemalggare.utils.DynamicController;
import com.grupp5.agila_schemalggare.utils.SceneManagerProvider;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDateTime;
import java.util.HashSet;

public class AdminMenuController implements DynamicController {

    @FXML
    private VBox passwordChangeContainer;
    @FXML
    private VBox roleChangeContainer;
    @FXML
    private VBox deleteAccountContainer;

    private LocalDateTime currentDate;
    private final HashSet<Account> accounts = AccountService.getRegisteredUsers();
    private final AccountService accountService = new AccountService();

    private void generatePasswordChangeContainer() {
        for (Account account : accounts) {
            Label titleLabel = new Label("Change " + account.getUsername() + "'s password:");
            HBox accountModal = new HBox(10);

            TextField passwordField = new TextField(account.getPassword());

            accountModal.getChildren().add(passwordField);
            passwordChangeContainer.getChildren().addAll(titleLabel, accountModal);
        }
    }

    private void generateRoleChangeContainer() {
        for (Account account : accounts) {
            Label titleLabel = new Label("Change " + account.getUsername() + "'s role:");
            HBox accountModal = new HBox(10);

            ToggleGroup toggleGroup = new ToggleGroup();
            ToggleButton adminRoleButton = new ToggleButton("Admin");
            ToggleButton userRoleButton = new ToggleButton("User");

            adminRoleButton.setToggleGroup(toggleGroup);
            userRoleButton.setToggleGroup(toggleGroup);

            if (account.getRole().equals("ADMIN")) {
                adminRoleButton.setSelected(true);
            } else {
                userRoleButton.setSelected(true);
            }

            accountModal.getChildren().addAll(adminRoleButton, userRoleButton);
            roleChangeContainer.getChildren().addAll(titleLabel, accountModal);
        }
    }

    private void generateDeleteAccountContainer() {
        for (Account account : accounts) {
            Label titleLabel = new Label("Delete " + account.getUsername() + "'s account:");
            HBox accountModal = new HBox(10);

            Button deleteAccountButton = new Button("Delete account");

            deleteAccountButton.setUserData(account.getUsername());

            deleteAccountButton.setOnAction(e -> deleteAccountAction(account.getUsername()));

            accountModal.getChildren().add(deleteAccountButton);
            deleteAccountContainer.getChildren().addAll(titleLabel, accountModal);
        }
    }

    @FXML
    private void saveAllChanges() {
        savePassWordChanges();
        saveRoleChanges();

        returnToCalendar();
    }

    private void savePassWordChanges() {
        String currentUsername = null;

        for (Node node : passwordChangeContainer.getChildren()) {
            if (node instanceof Label label) {
                String text = label.getText();
                currentUsername = text.substring(7, text.indexOf("'s password"));
            } else if (node instanceof HBox modal) {
                TextField passwordField = (TextField) modal.getChildren().getFirst();

                Account account = accountService.getUserByUsername(currentUsername);
                account.setPassword(passwordField.getText());
                accountService.updateAccountPassword(account);
            }
        }
    }

    private void saveRoleChanges() {
        String currentUsername = null;

        for (Node node : roleChangeContainer.getChildren()) {
            if (node instanceof Label label) {
                String text = label.getText();
                currentUsername = text.substring(7, text.indexOf("'s role"));

            } else if (node instanceof HBox modal) {
                ToggleButton adminToggle = (ToggleButton) modal.getChildren().get(0);

                Account account = accountService.getUserByUsername(currentUsername);

                if (adminToggle.isSelected()) {
                    accountService.promoteUserToAdmin(account);
                } else {
                    accountService.demoteAdminToUser(account);
                }
            }
        }
    }

    private void deleteAccountAction(String username) {
        Account account = accountService.getUserByUsername(username);
        if (account == null) return;

        accountService.deleteAccount(account);
        AccountService.removeRegisteredUser(account);

        passwordChangeContainer.getChildren().clear();
        roleChangeContainer.getChildren().clear();
        deleteAccountContainer.getChildren().clear();
        updateView();
    }

    private void returnToCalendar() {
        SceneManagerProvider.getSceneManager().switchScene("/com/grupp5/agila_schemalggare/calendar-viex.fxml", currentDate);
    }

  @Override
  public void updateView() {
    generatePasswordChangeContainer();
    generateRoleChangeContainer();
    generateDeleteAccountContainer();
  }

  @Override
  public void setCurrentDate(LocalDateTime currentDate) {
    this.currentDate = currentDate;
  }
}

