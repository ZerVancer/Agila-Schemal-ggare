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
        // För att testa så att events är synligt
        //this.calendar.populateEvents();
    }

    // Framtida användning för sparning/hämtning av konton från Repositories.
    public Account(UUID id, String username, String password) {
        this(username, password);
        this.id = id;
    }

    public void addEvent(Event event) {
        calendar.addEvent(event);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
    }

    // Getters
    public UUID getId() {
      return id;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
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

        if (object == null || !(object instanceof Account)) {
            return false;
        }

        Account account = (Account) object;
        return username.equals(account.username);
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    public abstract void setRole(String role);

    // Går alltid att lägga till saker i framtiden etc.
}
