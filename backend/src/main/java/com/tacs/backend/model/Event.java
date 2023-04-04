package com.tacs.backend.model;

import java.util.List;

public class Event {
    private String id;
    private String name;
    private String description;
    private User owerUser;
    private Status status;
    private List<EventOption> eventOptions;
    private List<User> registeredUsers;

    public Event() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getOwerUser() {
        return owerUser;
    }

    public void setOwerUser(User owerUser) {
        this.owerUser = owerUser;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<EventOption> getEventOptions() {
        return eventOptions;
    }

    public void setEventOptions(List<EventOption> eventOptions) {
        this.eventOptions = eventOptions;
    }

    public List<User> getRegisteredUsers() {
        return registeredUsers;
    }

    public void setRegisteredUsers(List<User> registeredUsers) {
        this.registeredUsers = registeredUsers;
    }
}
