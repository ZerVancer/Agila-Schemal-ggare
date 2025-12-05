package com.grupp5.agila_schemalggare.services;

import com.grupp5.agila_schemalggare.models.Account;
import com.grupp5.agila_schemalggare.models.Admin;
import com.grupp5.agila_schemalggare.models.User;
import com.grupp5.agila_schemalggare.utils.DynamicController;
import com.grupp5.agila_schemalggare.repositories.AccountFileRepository;

import java.util.ArrayList;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;

public class AccountService {
    AccountFileRepository accountFileRepository = new AccountFileRepository();

    private static HashSet<Account> registeredUsers = new HashSet<>();

    // La till denna "variabeln" för att verkligen deklarera ett konto som man kan använda sig utav
    // om så önskas i andra delar i projektet, istället för att behöva filtrera igenom och jämföra etc.
    private static Account loggedInAccount = null;
    private static final List<DynamicController> dynamicControllers = new ArrayList<>();

    public AccountService() {
        try {
            registeredUsers = accountFileRepository.loadAllAccountsFromFile();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Går att ta bort, skapar bara ett konto för att testa logiken.
        // Uppdaterade lite för att säkerställa att första kontot blir en "Admin"
//        if (registeredUsers.isEmpty()) {
//            registeredUsers.add(new Admin("First", "first"));
//            registeredUsers.add(new User("Second", "second"));
//        } else {
//            registeredUsers.add(new User("Second", "second"));
//        }
    }

    public static HashSet<Account> getRegisteredUsers() {
        return registeredUsers;
    }

    public static void removeRegisteredUser(Account account) {
        registeredUsers.remove(account);
    }

    public Account getUserByUsername(String username) {
        for (Account account : registeredUsers) {
            if (account.getUsername().equals(username)) {
                return account;
            }
        }
        return null;
    }

    // Getter för resterande komponenter för att hämta kontot om så önskas.
    public static Account getLoggedInAccount() {
        return loggedInAccount;
    }

    public static void setLoggedInAccount(Account account) {
        loggedInAccount = account;
    }

    public static void updateViews() {
        for (DynamicController controller : dynamicControllers) {
            controller.updateView();
        }
    }

    public static void addUpdator(DynamicController updator) {
        AccountService.dynamicControllers.add(updator);
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

        AccountFileRepository repository = new AccountFileRepository();

        try {
            repository.saveAccountToFile(account);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        boolean added = registeredUsers.add(account);

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

    public void updateAccountPassword(Account updatedAccount) {
        for (Account account : registeredUsers) {
            if (account.getUsername().equals(updatedAccount.getUsername())) {
                account.setPassword(updatedAccount.getPassword());
            }
            try {
                accountFileRepository.updateExistingAccount(account);
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        }
    }

    public void deleteAccount(Account account) {
        registeredUsers.remove(account);
        try {
            accountFileRepository.deleteExistingAccount(account);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void promoteUserToAdmin(Account user) {
        Admin admin = new Admin(user.getUsername(), user.getPassword());
        admin.setId(user.getId());
        admin.setCalendar(user.getCalendar());
        admin.setRole("ADMIN");

        registeredUsers.remove(user);
        try {
            accountFileRepository.deleteExistingAccount(user);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        registeredUsers.add(admin);
        try {
            accountFileRepository.saveAccountToFile(admin);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }


    public void demoteAdminToUser(Account admin) {
        User user = new User(admin.getUsername(), admin.getPassword());
        user.setId(admin.getId());
        user.setCalendar(user.getCalendar());
        user.setRole("USER");

        registeredUsers.remove(admin);
        try {
            accountFileRepository.deleteExistingAccount(admin);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        registeredUsers.add(user);
        try {
            accountFileRepository.saveAccountToFile(user);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
