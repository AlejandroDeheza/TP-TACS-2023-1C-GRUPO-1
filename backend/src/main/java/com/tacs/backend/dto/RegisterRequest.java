package com.tacs.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tacs.backend.security.ConfirmedField;
import com.tacs.backend.security.ValidPassword;
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
@ConfirmedField(originalField = "password", confirmationField = "passwordConfirmation")
public class RegisterRequest {
    @JsonProperty("first_name")
    @NotBlank(message = "First name can not be blank")
    @Schema(description = "User first name", example = "Juan")
    private String firstName;
    @JsonProperty("last_name")
    @NotBlank(message = "Last name can not be blank")
    @Schema(description = "User last name", example = "Perez")
    private String lastName;
    @JsonProperty("username")
    @NotBlank(message = "Username can not be blank")
    @Schema(description = "Username", example = "juan.perez")
    private String username;
    @JsonProperty("password")
    @NotBlank(message = "Password can not be blank")
    @ValidPassword
    @Schema(description = "Password", example = "mksiug_865K")
    private String password;
    @JsonProperty("password_confirmation")
    @NotBlank(message = "Password confirmation can not be blank")
    @Schema(description = "Confirmation password", example = "mksiug_865K")
    private String passwordConfirmation;
}
