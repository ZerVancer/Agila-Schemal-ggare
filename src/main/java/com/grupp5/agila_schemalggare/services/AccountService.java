package com.grupp5.agila_schemalggare.services;

import com.grupp5.agila_schemalggare.models.Account;
import com.grupp5.agila_schemalggare.models.Admin;
import com.grupp5.agila_schemalggare.models.User;

import java.util.HashSet;

public class AccountService {

    public HashSet<Account> registeredUsers = new HashSet<>();

    // La till denna "variabeln" för att verkligen deklarera ett konto som man kan använda sig utav
    // om så önskas i andra delar i projektet, istället för att behöva filtrera igenom och jämföra etc.
    private static Account loggedInAccount = null;

    public AccountService() {
        // Går att ta bort, skapar bara ett konto för att testa logiken.
        // Uppdaterade lite för att säkerställa att första kontot blir en "Admin"
        if (registeredUsers.isEmpty()) {
            registeredUsers.add(new Admin("First", "first"));
            registeredUsers.add(new User("Second", "second"));
        } else {
            registeredUsers.add(new User("Second", "second"));
        }
    }

    // Getter för resterande komponenter för att hämta kontot om så önskas.
    public static Account getLoggedInAccount() {
        return loggedInAccount;
    }

    public static void setLoggedInAccount(Account account) {
        loggedInAccount = account;
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

        loggedInAccount = account;

        setLoggedInAccount(loggedInAccount);

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

        Account account = registeredUsers.isEmpty() ? new Admin(username, password) : new User(username, password);

        boolean added = registeredUsers.add(account);
        //saveAccountToFile(); <-- empty method for later

        if (!added) {
            throw new IllegalArgumentException("Username already exists!");
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
