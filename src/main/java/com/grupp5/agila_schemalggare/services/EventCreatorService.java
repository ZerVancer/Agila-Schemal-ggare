package com.grupp5.agila_schemalggare.services;

import com.grupp5.agila_schemalggare.models.Account;
import com.grupp5.agila_schemalggare.models.Event;

import java.time.LocalDateTime;


//redundant file, functionality moved to CalendarService

public class EventCreatorService {
  public EventCreatorService() {}

  public void createEvent(Account account, String title, String desc, LocalDateTime startDate, LocalDateTime endDate) {
    Event event = new Event(title, desc, startDate, endDate);
    account.addEvent(event);
  }
}
