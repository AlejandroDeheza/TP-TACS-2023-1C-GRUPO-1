package com.tacs.telebot.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tianshuwang
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @JsonProperty("id")
    private String id = null;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("username")
    private String username;
}
