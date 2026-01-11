package com.crealink.app.exceptions;

import com.crealink.app.dto.response.ResponseStatus;

public class UnauthorizedException extends ApiException {
    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(ResponseStatus status) {
        super(status.getMessage());
    }

    public UnauthorizedException() {
        super(ResponseStatus.UNAUTHORIZED.getMessage());
    }
}
