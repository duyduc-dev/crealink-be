package com.crealink.app.exceptions;

import com.crealink.app.dto.response.ResponseStatus;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {

    private ResponseStatus responseStatus;

    public ApiException(ResponseStatus responseStatus) {
        super(responseStatus.getMessage());
        this.responseStatus = responseStatus;
    }

    public ApiException(ResponseStatus responseStatus, String message) {
        super(message);
        this.responseStatus = responseStatus;
    }
}
