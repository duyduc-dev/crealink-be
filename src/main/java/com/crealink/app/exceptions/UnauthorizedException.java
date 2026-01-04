package com.crealink.app.exceptions;

import com.crealink.app.dto.response.ResponseStatus;

public class UnauthorizedException extends ApiException {
    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException() {
        super(ResponseStatus.UNAUTHORIZED.getMessage());
    }
}

