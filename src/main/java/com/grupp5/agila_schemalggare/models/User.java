package com.grupp5.agila_schemalggare.models;

public class User extends Account {
    private String role;

    public User(String username, String password) {
        super(username, password);
        this.role = "USER";
    }

    @Override
    public String getRole() {
        return role;
    }
}
