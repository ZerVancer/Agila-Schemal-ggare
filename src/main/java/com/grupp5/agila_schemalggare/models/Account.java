package com.grupp5.agila_schemalggare.models;

import java.util.List;
import java.util.UUID;

// Simpel Account klass som tar emot username & password som input.
// Ska användas som basklass för resterande användar klasser.
public abstract class Account {
    private UUID id;
    private String username;
    private String password;
    private Calendar calendar = new Calendar();

    //Constructors
    public Account(String username, String password) {
        this.id = UUID.randomUUID();
        this.username = username;
        this.password = password;
    }

    // Framtida användning för sparning/hämtning av konton från Repositories.
    public Account(UUID id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public void addEvent(Event event) {
        calendar.addEvent(event);
    }

    // Getters
    public UUID getAccountId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Calendar getCalendar() {
      return  calendar;
    }

    public abstract String getRole();


    // Någon eventuell funktion för om användaren är Admin?
    public boolean canEditOthers() {
        return false;
    }

    // Går alltid att lägga till saker i framtiden etc.
}
