package com.grupp5.agila_schemalggare.services;

import com.grupp5.agila_schemalggare.controllers.CalendarDayController;
import com.grupp5.agila_schemalggare.controllers.EventServiceController;
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

    public void createEvent(String title, String desc, LocalDateTime startDate, LocalDateTime endDate) {
        if (!userIsLoggedIn()) {
            throw new IllegalStateException("User is not logged in");
        }

        Event event = new Event(title, desc, startDate, endDate);
        loggedInAccount.addEvent(event);

        //saveChangesToFile(); <-- empty method for later
    }

    public void deleteEvent(Event event) {
        if (!userIsLoggedIn()) {
            throw new IllegalStateException("User is not logged in");
        }

        Calendar calendar = loggedInAccount.getCalendar();

        calendar.removeEvent(event);
        AccountService.updateViews();

        //saveChangesToFile(); <-- empty method for later
    }

    public void editEvent(Event eventToEdit, String title, String desc, LocalDateTime startDate, LocalDateTime endDate) {
        if (!userIsLoggedIn()) {
            throw new IllegalStateException("User is not logged in");
        }

        if  (eventToEdit == null) {
            throw new IllegalArgumentException("No such event found.");
        }

        eventToEdit.setTitle(title);
        eventToEdit.setDescription(desc);
        eventToEdit.setStartDate(startDate);
        eventToEdit.setEndDate(endDate);

        //saveChangesToFile(); <-- empty method for later
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

    private boolean userIsLoggedIn() {
        return loggedInAccount != null;
    }

  public void openDayView(LocalDateTime date) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/grupp5/agila_schemalggare/calendarDayView.fxml"));
      Parent root = loader.load();

      CalendarDayController controller = loader.getController();
      controller.setCurrentDate(date);
      AccountService.addUpdator(controller);
      AccountService.updateViews();

      Stage stage = new Stage();
      stage.setTitle("Day View");
      stage.setScene(new Scene(root, 400, 800));
      stage.show();

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
