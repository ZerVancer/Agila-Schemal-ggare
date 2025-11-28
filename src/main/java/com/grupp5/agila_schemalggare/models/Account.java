package com.grupp5.agila_schemalggare.models;

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
        this.calendar = new Calendar();
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

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {}

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
        return calendar;
    }

    public abstract String getRole();

    // Någon eventuell funktion för om användaren är Admin?
    public boolean canEditOthers() {
        return false;
    }

    // Check för HashSet
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Account account = (Account) object;
        return username.equals(account.username);
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    // Går alltid att lägga till saker i framtiden etc.
}
