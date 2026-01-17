package com.crealink.app.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UsernameValidator
        implements ConstraintValidator<ValidUsername, String> {

    private static final String REGEX = "^[a-zA-Z0-9_.]+$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return false;
        }
        return value.matches(REGEX);
    }
}
