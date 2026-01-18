package com.crealink.app.exceptions;

import com.crealink.app.dto.response.ResponseStatus;

import lombok.Getter;

@Getter
public class ResourceAlreadyExistsException extends ApiException {

    public ResourceAlreadyExistsException(String message) {
        super(ResponseStatus.CONFLICT, message);
    }

    public ResourceAlreadyExistsException() {
        super(ResponseStatus.CONFLICT);
    }

    public ResourceAlreadyExistsException(ResponseStatus status) {
        super(status);
    }
}
