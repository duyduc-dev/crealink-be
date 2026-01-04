package com.crealink.app.exceptions;

import com.crealink.app.dto.response.ResponseStatus;

public class ResourceNotFoundException extends ApiException {
    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException() {
        super(ResponseStatus.NOT_FOUND.getMessage());
    }
}
