package com.crealink.app.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record SignInRequestDto(
    @NotBlank() String username,
    @NotBlank() String password 
) {
    
}
