package com.tacs.backend.model;

import com.tacs.backend.dto.EventDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
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
        VOTATION_PENDING,
        VOTATION_CLOSED,
        PENDING,
        STARTED,
        FINISHED;

        public Status lookUp(String name) {
            return Arrays.asList(Status.values())
                    .stream()
                    .filter(status -> status.name().equalsIgnoreCase(name))
                    .findFirst()
                    .orElseThrow(() -> new EnumConstantNotPresentException(Status.class, name));
        }
    }
}
