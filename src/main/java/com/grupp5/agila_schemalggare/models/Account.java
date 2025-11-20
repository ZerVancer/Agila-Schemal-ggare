package com.grupp5.agila_schemalggare.models;

import java.util.UUID;

// Simpel Account klass som tar emot username & password som input.
// Ska användas som basklass för resterande användar klasser.
public abstract class Account {
    private UUID id;
    private String username;
    private String password;
    //private Calendar calendar; - Placeholder för tillfället.

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

    // Getters
    public UUID getAccountId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    // Möjligtvis värt att ta bort senare?
    public String getPassword() {
        return password;
    }

    public abstract String getRole();

    // Någon eventuell funktion för om användaren är Admin? Inte nödvändig just nu.
    public boolean canEditOthers() {
        return false;
    }

    // Går alltid att lägga till saker i framtiden etc.
}
