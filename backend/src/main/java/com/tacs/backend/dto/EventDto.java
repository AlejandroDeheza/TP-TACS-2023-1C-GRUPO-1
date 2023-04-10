package com.tacs.backend.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Data
@Getter
@Setter
@Builder
public class EventDto {
    private String name;
    private String description;
    private Set<EventOptionDto> eventOptions;
    private UserDto ownerUser;
    private String status;
    private Set<UserDto> registeredUsers;

}
