package com.crealink.app.dto.auth;

import java.time.LocalDate;

import com.crealink.app.validators.ValidUsername;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SignupRequestDto(
        @NotBlank() @ValidUsername @Size(min = 3, max = 30, message = "This field must have size between 3 and 30 characters") String username,
        @NotBlank() String firstName,
        @NotBlank() String lastName,
        @NotBlank() @Email() String email,
        @NotBlank() String password,
        @NotNull() LocalDate dateOfBirth) {
}
