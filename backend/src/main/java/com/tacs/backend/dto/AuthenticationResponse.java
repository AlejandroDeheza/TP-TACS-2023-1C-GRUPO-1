package com.tacs.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    @JsonProperty("id")
    @Schema(hidden = true)
    private String id;
    @JsonProperty("first_name")
    @Schema(hidden = true)
    private String firstName;
    @JsonProperty("last_name")
    @Schema(hidden = true)
    private String lastName;
    @JsonProperty("username")
    @Schema(hidden = true)
    private String username;
    @JsonProperty("access_token")
    private String accessToken;
}
