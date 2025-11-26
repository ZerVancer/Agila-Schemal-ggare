package com.grupp5.agila_schemalggare.services;

import com.grupp5.agila_schemalggare.models.Account;
import com.grupp5.agila_schemalggare.models.Calendar;
import com.grupp5.agila_schemalggare.models.Event;
import com.grupp5.agila_schemalggare.models.User;

import java.time.LocalDateTime;

public class CalendarService {
    // AccountService accountService = new AccountService(); //solve dependency injection at a later point
    // Account loggedInAccount = accountService.getLoggedInAccount(); <-- fetch loggedInAccount later when variable is available

    Account loggedInAccount = new User("milo_soder", "smörgåsrån"); //tillfällig användare

    public Account createEvent(String title, String desc, LocalDateTime startDate, LocalDateTime endDate) {
        if (!userIsLoggedIn()) {
            throw new IllegalStateException("User is not logged in");
        }

        Calendar calendar = loggedInAccount.getCalendar();

        Event event = new Event(title, desc, startDate, endDate);
        calendar.addEvent(event);

        //saveChangesToFile(); <-- empty method for later

        return loggedInAccount;
    }

    public Account deleteEvent(Event event) {
        if (!userIsLoggedIn()) {
            throw new IllegalStateException("User is not logged in");
        }

        Calendar calendar = loggedInAccount.getCalendar();

        for (Event e : calendar.getEvents()) {
            if  (e.getId().equals(event.getId())) {
                calendar.removeEvent(e);
            }
        }

        //saveChangesToFile(); <-- empty method for later

        return loggedInAccount;
    }

    public Account editEvent(Event eventToEdit, String title, String desc, LocalDateTime startDate, LocalDateTime endDate) {
        if (!userIsLoggedIn()) {
            throw new IllegalStateException("User is not logged in");
        }

        Calendar calendar = loggedInAccount.getCalendar();

        Event editedEvent = null;

        for (Event e : calendar.getEvents()) {
            if (e.getId().equals(eventToEdit.getId())) {
                editedEvent = e;
            }
        }

        if  (editedEvent == null) {
            throw new IllegalArgumentException("No such event found.");
        }

        editedEvent.setTitle(title);
        editedEvent.setDescription(desc);
        editedEvent.setStartDate(startDate);
        editedEvent.setEndDate(endDate);

        //saveChangesToFile(); <-- empty method for later

        return loggedInAccount;
    }

    //room for possible extra calendar display logic here
    //<<<---->>>

    private boolean userIsLoggedIn() {
        return loggedInAccount != null;
    }

    // private void saveChangesToFile() {}
}
