package com.tacs.telebot.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;

    @JsonProperty("status")
    private String status;

    @JsonProperty("event_options")
    private Set<EventOption> eventOptions;

    @JsonProperty("owner_user")
    private User ownerUser;

    @JsonProperty("registered_users")
    private Set<User> registeredUsers;

    @JsonProperty("create_date")
    private Date createDate;

    @JsonCreator
    public Event(@JsonProperty(value = "name", required = true) String name,
                          @JsonProperty(value = "events_options", required = true) Set<EventOption> eventOptions) {
        this.name = name;
        this.eventOptions = eventOptions;
    }

}
