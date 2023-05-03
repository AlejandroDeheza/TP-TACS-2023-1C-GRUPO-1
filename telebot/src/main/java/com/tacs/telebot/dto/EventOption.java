package com.tacs.telebot.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventOption {
    @JsonProperty("id")
    private String id = null;
    @JsonProperty("date_time")
    private Date dateTime;

    @JsonProperty("vote_quantity")
    private long voteQuantity;

    @JsonProperty("vote_users")
    private List<User> voteUsers;

    @JsonProperty("update_time")
    private Date updateDate;

    @JsonProperty("event_name")
    private String eventName;

    @JsonCreator
    public EventOption(@JsonProperty(value = "date_time", required = true) Date dateTime) {
        this.dateTime = dateTime;
    }
}
