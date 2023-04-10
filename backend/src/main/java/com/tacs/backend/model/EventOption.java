package com.tacs.backend.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class EventOption {
    private String id;
    private Event event;
    private LocalDate date;
    private LocalTime hour;
    private List<User> users;

}
