package com.tacs.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tacs.backend.model.Event;
import com.tacs.backend.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventOptionDto {
    @JsonProperty("id")
    private String id;
    @JsonProperty("date")
    @NotBlank(message = "Date can not be blank")
    @Schema(description = "Event option date", example = "2023-04-14")
    private LocalDate date;
    @JsonProperty("time")
    @NotBlank(message = "Time can not be blank")
    @Schema(description = "Event option time", example = "10:00")
    private LocalTime time;

    @JsonProperty("vote_quantity")
    @Schema(description = "Event vote quantity")
    private long voteQuantity;

}
