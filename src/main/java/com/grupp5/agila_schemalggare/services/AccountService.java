package com.grupp5.agila_schemalggare.services;

import com.grupp5.agila_schemalggare.models.Account;
import com.grupp5.agila_schemalggare.models.Admin;
import com.grupp5.agila_schemalggare.models.User;

import java.util.ArrayList;
import java.util.List;

public class AccountService {

    public List<Account> registeredUsers = new ArrayList<>();

    public AccountService() {
        // Går att ta bort, skapar bara ett konto för att testa logiken.
        registeredUsers.add(new User("Joel", "1234"));
    }

    public Account loginUser(String username, String password) {

        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username is required!");
        }

        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password is required!");
        }

        Account account = registeredUsers.stream()
                .filter(acc -> acc.getUsername().equals(username))
                .findFirst()
                .orElse(null);

        if (account == null) {
            return null;
        }

        if (!account.getPassword().equals(password)) {
            return null;
        }

        return account;
    }

    public boolean registerNewAccount(String username, String password) {
        if (!validateRegisteredUsername(username)) {
            throw new IllegalArgumentException("Invalid username!\n" +
                    "Username cannot contain special characters or blank spaces,\n" +
                    "and be between 6 and 15 characters long.");
        }

        if (!validateRegisteredUserPassword(password)) {
            throw new IllegalArgumentException("Invalid password!\n" +
                    "Password must contain at least one special character,\n" +
                    "and be between 6 and 30 characters long.");
        }

        Account account;

        if (registeredUsers.isEmpty()) {
            account = new Admin(username, password);
        } else {
            account = new User(username, password);
        }

        registeredUsers.add(account);
        //saveAccountToFile(); <-- empty method for later

        for (Account a : registeredUsers) {
            System.out.println(a.toString());
        }

        return true;
    }

    private boolean validateRegisteredUsername(String username) { //later also validate that no two users can have the same username
        return username.length() > 5 && //longer than 5 characters
                username.length() < 16 && //shorter than 16 characters
                username.matches("[A-Za-z0-9_.-]+"); //allows letters, numbers, -, _, and .
    }

    private boolean validateRegisteredUserPassword(String password) {
        return password.length() > 5 && //longer than 5 characters
                password.length() < 31 && //shorter than 31
                password.matches(".*[!@#$%^&*()_+=\\-{}\\[\\]:;\"'<>,.?/].*"); //must contain at least one special character
    }

    //private void saveAccountToFile() {}
}
