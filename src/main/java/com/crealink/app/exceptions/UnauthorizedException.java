package com.crealink.app.exceptions;

import com.crealink.app.dto.response.ResponseStatus;

public class UnauthorizedException extends ApiException {
    public UnauthorizedException(String message) {
        super(ResponseStatus.UNAUTHORIZED, message);
    }

    public UnauthorizedException(ResponseStatus status) {
        super(status);
    }

    public UnauthorizedException() {
        super(ResponseStatus.UNAUTHORIZED);
    }
}
