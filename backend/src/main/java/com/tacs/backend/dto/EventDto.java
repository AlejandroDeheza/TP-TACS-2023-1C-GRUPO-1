package com.tacs.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tacs.backend.model.EventOption;
import com.tacs.backend.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventDto {
    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    @NotBlank(message = "Name can not be blank")
    @Schema(description = "Event name", example = "Event name")
    private String name;
    @JsonProperty("description")
    @Schema(description = "Event description", example = "Event description")
    private String description;

    @JsonProperty("status")
    @Schema(description = "Event status")
    private String status;

    @JsonProperty("event_options")
    @Schema(description = "Event options")
    private Set<EventOptionDto> eventOptions;

    @JsonProperty("owner_user")
    private UserDto ownerUser;

    @JsonProperty("registered_users")
    private Set<UserDto> registeredUsers;

}
