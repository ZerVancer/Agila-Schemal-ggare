package com.grupp5.agila_schemalggare.models;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

public class Event implements Serializable {
    UUID id;
    String title;
    String description;
    Instant startDate;
    Instant endDate; //Instant to save in UTC format rather than LocalDateTime to avoid issues if user crosses time zones

    public Event(String title, String desc, LocalDateTime startDate, LocalDateTime endDate) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.description = desc;
        this.startDate = startDate.toInstant(ZoneOffset.UTC);
        this.endDate = endDate.toInstant(ZoneOffset.UTC);
    }

    // Setters
    public void setTitle(String title) {
      this.title = title;
    }
    public void setDescription(String description) {
      this.description = description;
    }
    public void setStartDate(LocalDateTime startDate) {
    this.startDate = startDate.toInstant(ZoneOffset.UTC);
  }
    public void setEndDate(LocalDateTime endDate) {
    this.endDate = endDate.toInstant(ZoneOffset.UTC);
  }

    // Getters
    public UUID getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public LocalDateTime getStartDate() {
    return LocalDateTime.ofInstant(startDate, ZoneOffset.UTC);
  }
    public LocalDateTime getEndDate() { //getters send out LocalDateTime to display time in user's time zone
    return LocalDateTime.ofInstant(endDate, ZoneOffset.UTC);
  }

}
