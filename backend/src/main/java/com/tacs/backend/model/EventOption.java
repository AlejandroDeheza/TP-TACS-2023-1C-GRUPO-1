package com.tacs.backend.model;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class EventOption {

    private String id;
    private Event event;
    private LocalDate date;
    private LocalTime hour;
    private List<User> users;

    public EventOption() {
        // TODO document why this constructor is empty
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getHour() {
        return hour;
    }

    public void setHour(LocalTime hour) {
        this.hour = hour;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
