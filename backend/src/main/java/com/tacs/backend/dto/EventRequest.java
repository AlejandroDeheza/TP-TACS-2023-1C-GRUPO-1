package com.tacs.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventRequest {
    @JsonProperty("name")
    @NotBlank(message = "Name can not be blank")
    @Schema(description = "Event name", example = "Event name")
    private String name;
    @JsonProperty("description")
    @Schema(description = "Event description", example = "Event description")
    private String description;

    @JsonProperty("event_options")
    @Schema(description = "Event options")
    private Set<EventOptionRequest> eventOptions;

}
