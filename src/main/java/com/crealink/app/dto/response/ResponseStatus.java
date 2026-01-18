package com.crealink.app.dto.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseStatus {
    OK("200000", "Success", HttpStatus.OK),

    BAD_REQUEST("400000", "Bad Request", HttpStatus.BAD_REQUEST),
    VALIDATE_FAILED("400001", "Validate failed", HttpStatus.BAD_REQUEST),

    CONFLICT("409000", "Conflict", HttpStatus.CONFLICT),
    EMAIL_ALREADY_EXISTS("409001", "Email already exists", HttpStatus.CONFLICT),
    USERNAME_EXISTS("409002", "Username already exists", HttpStatus.CONFLICT),

    NOT_FOUND("404000", "Resource Not Found", HttpStatus.NOT_FOUND),

    UNAUTHORIZED("401000", "Unauthorized or Access Token is expired.", HttpStatus.UNAUTHORIZED),
    USERNAME_PASSWORD_INVALID("401001", "The email or password you entered is invalid.", HttpStatus.UNAUTHORIZED),

    INTERNAL_SERVER_ERROR("500000", "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}
