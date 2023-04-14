package com.tacs.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class EventOptionDto {
    private String id;
    private EventDto event;
    private LocalDate date;
    private LocalTime hour;
    private Set<UserDto> users;

}
