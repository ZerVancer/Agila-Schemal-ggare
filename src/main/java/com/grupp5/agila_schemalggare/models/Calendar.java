package com.grupp5.agila_schemalggare.models;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Calendar {
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

    // Metod för att skapa några events. - Joel
    // Går att radera om så önskas, eller fylla på med mer för redovisningen?

  public void populateEvents() {

      LocalDateTime now = LocalDateTime.now();

      events.add(new Event("Standup-möte", "Daglig avstämning med teamet", now.withHour(9).withMinute(0), now.withHour(9).withMinute(15)));
      events.add(new Event("Focus hour", "Gå igenom Spring boot", now.withHour(12).withMinute(0), now.withHour(13).withMinute(5)));

      // Event om två dagar, kl 13–14
      events.add(new Event("Kundpresentation", "Presentation av sprintens resultat", now.plusDays(2).withHour(13).withMinute(0), now.plusDays(2).withHour(14).withMinute(0)));

      // Event nästa vecka
      events.add(new Event("Planeringsmöte", "Sprint planning", now.plusDays(7).withHour(10).withMinute(0), now.plusDays(7).withHour(12).withMinute(0)));

      // Event nästa månad för att testa månadsvyn
      LocalDateTime nextMonth = now.plusMonths(1).withDayOfMonth(3);
      events.add(new Event("Retro", "Sprint retrospective", nextMonth.withHour(15).withMinute(0), nextMonth.withHour(16).withMinute(0)));
  }
}
