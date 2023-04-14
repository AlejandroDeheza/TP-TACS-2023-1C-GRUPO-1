package com.tacs.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventOptionRequest {
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

}
