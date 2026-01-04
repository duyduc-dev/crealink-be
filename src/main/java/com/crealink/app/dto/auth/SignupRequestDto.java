package com.crealink.app.dto.auth;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SignupRequestDto (
    @NotBlank() String username,
    @NotBlank() String firstName,
    @NotBlank() String lastName,
    @NotBlank() @Email() String email,
    @NotBlank() String password,
    @NotNull() LocalDate dateOfBirth
) {
}
