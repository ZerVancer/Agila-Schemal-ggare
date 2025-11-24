package com.grupp5.agila_schemalggare.services;

import com.grupp5.agila_schemalggare.models.Account;
import com.grupp5.agila_schemalggare.models.Admin;
import com.grupp5.agila_schemalggare.models.User;

import java.util.ArrayList;
import java.util.List;

public class AccountService {

    public List<Account> registeredUsers = new ArrayList<>();

    // La till denna "variabeln" för att verkligen deklarera ett konto som man kan använda sig utav
    // om så önskas i andra delar i projektet, istället för att behöva filtrera igenom och jämföra etc.
    public Account loggedInAccount = null;

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
    public Account getLoggedInAccount() {
        return loggedInAccount;
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

        return account;
    }

    // Metod för register.
//    public Account registerNewAccount() {
//
//    }
}
