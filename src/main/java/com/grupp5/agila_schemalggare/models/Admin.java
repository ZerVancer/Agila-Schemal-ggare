package com.grupp5.agila_schemalggare.models;

public class Admin extends Account {
    private String role;

    public Admin(String username, String password) {
        super(username, password);
        this.role = "ADMIN";
    }

    @Override
    public String getRole() {
        return role;
    }

    @Override
    public void setRole(String role) {
        this.role = role;
    }
}