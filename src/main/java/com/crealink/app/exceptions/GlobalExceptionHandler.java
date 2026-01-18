package com.crealink.app.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.crealink.app.dto.response.ResponseDto;
import com.crealink.app.dto.response.ResponseStatus;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto<?>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        StringBuilder sb = new StringBuilder();
        ex
                .getBindingResult()
                .getFieldErrors()
                .forEach(error -> sb.append(error.getField()).append(": ").append(error.getDefaultMessage())
                        .append("; "));
        ResponseDto<?> response = new ResponseDto<>(ResponseStatus.BAD_REQUEST, sb.toString().trim());
        return new ResponseEntity<>(response, ResponseStatus.BAD_REQUEST.getHttpStatus());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseDto<?>> handleConstraintViolation(
            ConstraintViolationException ex) {

        String message = ex.getConstraintViolations()
                .iterator()
                .next()
                .getMessage();

        ResponseDto<?> response = new ResponseDto<>(ResponseStatus.VALIDATE_FAILED, message);
        return new ResponseEntity<>(response, ResponseStatus.VALIDATE_FAILED.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto<?>> handleGeneric(Exception ex) {
        log.error(ex.getMessage());
        ResponseDto<?> response = new ResponseDto<>(ResponseStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        return new ResponseEntity<>(response, ResponseStatus.INTERNAL_SERVER_ERROR.getHttpStatus());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ResponseDto<?>> handleUnauthorizedException(UnauthorizedException ex) {
        ResponseDto<?> response = new ResponseDto<>(ex.getResponseStatus(), ex.getMessage());
        return new ResponseEntity<>(response, ex.getResponseStatus().getHttpStatus());
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ResponseDto<?>> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex) {
        ResponseDto<?> response = new ResponseDto<>(ex.getResponseStatus(), ex.getMessage());
        return new ResponseEntity<>(response, ex.getResponseStatus().getHttpStatus());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseDto<?>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ResponseDto<?> response = new ResponseDto<>(ex.getResponseStatus(), ex.getMessage());
        return new ResponseEntity<>(response, ex.getResponseStatus().getHttpStatus());
    }
}
