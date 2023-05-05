package com.tacs.telebot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

/**
 * @author tianshuwang
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("User")
public class UserData implements Serializable {
    @JsonProperty("id")
    private String id = null;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("username")
    private String username;

    @JsonProperty("token")
    private String token;
}
