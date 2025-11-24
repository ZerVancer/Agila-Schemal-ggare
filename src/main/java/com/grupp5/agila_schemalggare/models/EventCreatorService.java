package com.grupp5.agila_schemalggare.models;

import java.time.LocalDateTime;

public class EventCreatorService {
  public EventCreatorService() {}

  public void createEvent(Account account, String title, String desc, LocalDateTime startDate, LocalDateTime endDate) {
    Event event = new Event(title, desc, startDate, endDate);
    account.addEvent(event);
  }
}
