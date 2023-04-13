package com.tacs.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tacs.backend.utils.ConfirmedField;
import com.tacs.backend.utils.ValidPassword;
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
    private String firstName;
    @JsonProperty("last_name")
    @NotBlank(message = "Last name can not be blank")
    private String lastName;
    @JsonProperty("username")
    @NotBlank(message = "Username can not be blank")
    private String username;
    @JsonProperty("password")
    @NotBlank(message = "Password can not be blank")
    @ValidPassword
    private String password;
    @JsonProperty("password_confirmation")
    @NotBlank(message = "Password confirmation can not be blank")
    private String passwordConfirmation;
}
