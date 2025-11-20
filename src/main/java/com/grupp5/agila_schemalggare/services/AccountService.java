package com.grupp5.agila_schemalggare.services;

import com.grupp5.agila_schemalggare.models.Account;
import com.grupp5.agila_schemalggare.models.User;

import java.util.ArrayList;
import java.util.List;

public class AccountService {

    public List<Account> registeredUsers = new ArrayList<>();

    public AccountService() {
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
}
