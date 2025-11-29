package com.grupp5.agila_schemalggare.repositories;

import com.grupp5.agila_schemalggare.models.Account;
import com.grupp5.agila_schemalggare.models.Admin;
import com.grupp5.agila_schemalggare.models.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AccountFileRepository {
    public boolean saveAccountToFile(Account account) throws IOException {
        File folder = new File("accountsSaveData");

        if (!folder.exists()) {
            folder.mkdir();
        }

        String fileName = account.getRole() + "@" + account.getUsername() + ".ser";

        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(
                        new File(folder, fileName)))) {
            out.writeObject(account);
        }
        return true;
    }

    public Account loadSingleAccountFromFile(String username) throws IOException, ClassNotFoundException {
        Account account;

        File folder = new File("accountsSaveData");

        if (!folder.exists()) {
            throw new NullPointerException("Folder doesn't exist");
        }

        for (File file : folder.listFiles()) {
            String[] accountFileName = file.getName().split("@"); //splits "ADMIN@username.ser" to ["ADMIN", "username.ser"]
            accountFileName[1] = accountFileName[1].substring(0, accountFileName[1].length() - 4); //removes .ser from "username.ser"

            if (accountFileName[1].equals(username)) {
                try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
                    if ("ADMIN".equals(accountFileName[0])) {
                        return (Admin) in.readObject();
                    } else if ("USER".equals(accountFileName[0])) {
                        return (User) in.readObject();
                    }
                }
            }
        }
        return null;
    }

    public List<Account> loadAllAccountsFromFile() throws IOException, ClassNotFoundException {
        ArrayList<Account> accounts = new ArrayList<>();

        File folder = new File("accountsSaveData");

        if (!folder.exists()) {
            throw new NullPointerException("Folder doesn't exist");
        }

        for (File file : folder.listFiles()) {
            String[] accountFileName = file.getName().split("@");

            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
                if ("ADMIN".equals(accountFileName[0])) {
                    accounts.add((Admin) in.readObject());
                } else if ("USER".equals(accountFileName[0])) {
                    accounts.add((User) in.readObject());
                }
            }
        }
        return accounts;
    }

    public boolean updateExistingAccount(Account account) throws IOException {
        File folder = new File("accountsSaveData");

        if (!folder.exists()) {
            throw new FileNotFoundException("Folder doesn't exist!");
        }

        for (File file : folder.listFiles()) {
            String[] accountFileName = file.getName().split("@");
            accountFileName[1] = accountFileName[1].substring(0, accountFileName[1].length() - 4);

            if (accountFileName[1].equals(account.getUsername())) {
                try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
                    out.writeObject(account);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean deleteExistingAccount(Account account) throws IOException {
        File folder = new File("accountsSaveData");

        if (!folder.exists()) {
            throw new NullPointerException("Folder doesn't exist!");
        }

        for (File file : folder.listFiles()) {
            String[] accountFileName = file.getName().split("@");
            accountFileName[1] = accountFileName[1].substring(0, accountFileName[1].length() - 4);

            if (accountFileName[1].equals(account.getUsername())) {
                return file.delete();
            }
        }
        return false;
    }
}
