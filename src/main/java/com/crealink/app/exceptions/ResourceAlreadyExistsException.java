package com.crealink.app.exceptions;

import com.crealink.app.dto.response.ResponseStatus;

public class ResourceAlreadyExistsException extends ApiException {
    public ResourceAlreadyExistsException(String message) {
        super(message);
    }

    public ResourceAlreadyExistsException() {
        super(ResponseStatus.CONFLICT.getMessage());
    }
}
