package com.tacs.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("events")
public class Event {
    @Id
    private String id;
    private String name;
    private String description;
    @DBRef
    @Field("owner_user")
    private User ownerUser;
    private Status status;
    @DBRef
    @Field("event_options")
    private Set<EventOption> eventOptions = new HashSet<>();
    @DBRef
    @Field("registered_users")
    private Set<User> registeredUsers = new HashSet<>();


    public enum Status {
        /**
         * vote pending
         */
        VOTE_PENDING,
        /**
         * vote closed
         */
        VOTE_CLOSED,
        /**
         * pending
         */
        PENDING,
        /**
         * started
         */
        STARTED,
        /**
         * finished
         */
        FINISHED
    }
}
