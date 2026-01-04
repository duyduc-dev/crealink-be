package com.crealink.app.dto.user;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;

@Builder
public record UserDto (
    String id,
    String username,
    String firstName,
    String lastName,
    String email,
    LocalDate dateOfBirth,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    @JsonIgnore Long internalId,
    @JsonIgnore String password,
    @JsonIgnore String passwordSalt
) {}
