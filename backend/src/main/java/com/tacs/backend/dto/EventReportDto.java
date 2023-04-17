package com.tacs.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventReportDto {
    @JsonProperty("created_event_counter")
    @Schema(description = "Created event counter")
    private int createdEventCounter;

    @JsonProperty("options_voted_counter")
    @Schema(description = "Options voted counter")
    private int optionsVotedCounter;

    //@JsonProperty("id")
    //@Schema(description = "Most voted options")
    //private List<String> mostVotedOptions;
}
