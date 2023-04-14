package com.tacs.backend.model;

import com.tacs.backend.dto.EventDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;
import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Event {
    private String id;
    private String name;
    private String description;
    private User owerUser;
    private Status status;
    private List<EventOption> eventOptions;
    private List<User> registeredUsers;

    public EventDto transformToDto() {
        return EventDto.builder()
                .name(this.name)
                .description(this.description)
                .status(this.status.name())
                .build();
    }

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
