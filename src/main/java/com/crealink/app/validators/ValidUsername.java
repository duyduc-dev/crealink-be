package com.crealink.app.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = UsernameValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUsername {

    String message() default
            "Username may contain only letters, numbers, underscore (_) and dot (.) without spaces.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}