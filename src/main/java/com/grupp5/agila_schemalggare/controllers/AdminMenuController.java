package com.grupp5.agila_schemalggare.controllers;

import com.grupp5.agila_schemalggare.models.Account;
import com.grupp5.agila_schemalggare.services.AccountService;
import com.grupp5.agila_schemalggare.utils.SceneManagerProvider;
import com.grupp5.agila_schemalggare.utils.ServiceRegister;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.HashSet;

public class AdminMenuController implements ServiceRegister {

    @FXML
    private VBox passwordChangeContainer;
    @FXML
    private VBox roleChangeContainer;

    @FXML
    private Button saveAndReturnButton;

    private AccountService accountService;
    private HashSet<Account> accounts;

    public AdminMenuController() {
    }

    @Override
    public void registerAccountService(AccountService accountService) {
        this.accountService = accountService;
        this.accounts = accountService.getRegisteredUsers();

        populateMenuContainer();
    }

    private void populateMenuContainer() {
        generatePasswordChangeContainer();
        generateRoleChangeContainer();
    }

    private void generatePasswordChangeContainer() {
        for (Account account : accounts) {
            HBox accountModal = new HBox(10);

            Label accountLabel = new Label(account.getUsername());
            TextField passwordField = new TextField(account.getPassword());

            accountModal.getChildren().addAll(accountLabel, passwordField);
            passwordChangeContainer.getChildren().add(accountModal);
        }
    }

    private void generateRoleChangeContainer() {
        for (Account account : accounts) {
            HBox accountModal = new HBox(10);

            Label accountLabel = new Label(account.getUsername());
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

            accountModal.getChildren().addAll(accountLabel, adminRoleButton, userRoleButton);
            roleChangeContainer.getChildren().add(accountModal);
        }
    }

    @FXML
    private void saveAllChanges() {

        savePassWordChanges();
        saveRoleChanges();

        returnToCalendar();
    }

    private void savePassWordChanges() {
        for (Node node : passwordChangeContainer.getChildren()) {
            if (node instanceof HBox modal) {
                Label accountLabel = (Label) modal.getChildren().get(0);
                TextField passwordField = (TextField) modal.getChildren().get(1);

                String username = accountLabel.getText();
                String password = passwordField.getText();

                Account account = accountService.getUserByUsername(username);
                account.setPassword(password);
                accountService.updateAccountPassword(account);
            }
        }
    }

    private void saveRoleChanges() {
        for (Node node : roleChangeContainer.getChildren()) {
            if (node instanceof HBox modal) {
                Label accountLabel = (Label) modal.getChildren().get(0);
                String username = accountLabel.getText();

                ToggleButton adminToggle = (ToggleButton) modal.getChildren().get(1);

                if (adminToggle.isSelected()) {
                    Account account = accountService.getUserByUsername(username);
                    accountService.promoteUserToAdmin(account);
                } else {
                    Account account = accountService.getUserByUsername(username);
                    accountService.demoteAdminToUser(account);
                }
            }
        }
    }

    private void returnToCalendar() {
        SceneManagerProvider.getSceneManager().switchScene("/com/grupp5/agila_schemalggare/calendar-viex.fxml");
    }
}

