package com.grupp5.agila_schemalggare.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Calendar implements Serializable {
  private ArrayList<Event> events = new ArrayList<>();

  public Calendar() {}
  public Calendar(ArrayList<Event> events) {
    this.events = events;
  }

  public void addEvent(Event event) {
    events.add(event);
  }

  public void removeEvent(Event event) {
    events.remove(event);
  }

  public ArrayList<Event> getEvents() {
    return events;
  }
}
