package com.tacs.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    @JsonProperty("username")
    @NotBlank(message = "Username can not be blank")
    private String username;
    @JsonProperty("password")
    @NotBlank(message = "Password can not be blank")
    private String password;
}
