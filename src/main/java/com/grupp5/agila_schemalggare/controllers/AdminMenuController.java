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
    private VBox menuContainer;

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
        accounts = accountService.getRegisteredUsers();
        for (Account account : accounts) {
            HBox accountModal = new HBox(10);

            Label accountLabel = new Label(account.getUsername());
            TextField passwordField = new TextField(account.getPassword());

            accountModal.getChildren().addAll(accountLabel, passwordField);
            menuContainer.getChildren().add(accountModal);
        }
    }

    @FXML
    private void saveAllChanges() {

        savePassWordChanges();

        returnToCalendar();
    }

    private void savePassWordChanges() {
        for (Node node : menuContainer.getChildren()) {
            if (node instanceof HBox modal) {
                Label accountLabel = (Label) modal.getChildren().get(0);
                TextField passwordField = (TextField) modal.getChildren().get(1);

                String username = accountLabel.getText();
                String password = passwordField.getText();

                Account account = accountService.getUserByUsername(username);
                account.setPassword(password);
                accountService.updateAccountPassword(account);


                for (Account a : accounts) {
                    if (a.getUsername().equals(account.getUsername())) {
                        a.setPassword(account.getPassword());
                    }

                    System.out.println(a.getPassword() + " am");
                }
            }
        }
    }

    private void returnToCalendar() {
        SceneManagerProvider.getSceneManager().switchScene("/com/grupp5/agila_schemalggare/calendar-viex.fxml");
    }
}

