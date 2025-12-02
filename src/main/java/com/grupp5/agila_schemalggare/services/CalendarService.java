package com.grupp5.agila_schemalggare.services;

import com.grupp5.agila_schemalggare.controllers.CalendarServiceController;
import com.grupp5.agila_schemalggare.models.Account;
import com.grupp5.agila_schemalggare.models.Calendar;
import com.grupp5.agila_schemalggare.models.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class CalendarService {
    Account loggedInAccount = AccountService.getLoggedInAccount(); //<-- fetch loggedInAccount later when variable is available

    //Account loggedInAccount = new User("milo_soder", "smörgåsrån"); //tillfällig användare

    public Account createEvent(String title, String desc, LocalDateTime startDate, LocalDateTime endDate) {
        if (!userIsLoggedIn()) {
            throw new IllegalStateException("User is not logged in");
        }

        Event event = new Event(title, desc, startDate, endDate);
        loggedInAccount.addEvent(event);

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
                return loggedInAccount;
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

    public List<Event> getAllEvents() {
        return loggedInAccount.getCalendar().getEvents();
    }

    public List<Event> getSpecificEvent(LocalDateTime date) {
        return getAllEvents()
                .stream()
                .filter(event -> event.getStartDate().toLocalDate().equals(date.toLocalDate()))
                .toList();
    }

    public void openEventHandlingWindow(LocalDateTime currentDate) {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/grupp5/agila_schemalggare/calendarService.fxml"));
      try {
        Parent root = loader.load();
        CalendarServiceController controller = loader.getController();
        controller.setCurrentDate(currentDate);
        controller.setScene();
        Stage stage = new  Stage();
        stage.setTitle("Event Handling");
        stage.setScene(new Scene(root, 300, 300));
        stage.show();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

    //room for possible extra calendar display logic here
    //<<<---->>>

    private boolean userIsLoggedIn() {
        return loggedInAccount != null;
    }

    // private void saveChangesToFile() {}
}
