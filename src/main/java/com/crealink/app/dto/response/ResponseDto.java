package com.crealink.app.dto.response;

import lombok.Data;

@Data
public class ResponseDto<T extends Object> {
    private String status;
    private T data;
    private String message;

    public ResponseDto(ResponseStatus status, T data, String message) {
        this.status = status.getCode();
        this.data = data;
        this.message = message;
    }
    
    public ResponseDto(ResponseStatus status, String message) {
        this.status = status.getCode();
        this.data = null;
        this.message = message;
    }

    public ResponseDto(ResponseStatus status) {
        this.status = status.getCode();
        this.data = null;
        this.message = status.getMessage();
    }

    public ResponseDto(T data) {
        this.status = ResponseStatus.OK.getCode();
        this.data = data;
        this.message = ResponseStatus.OK.getMessage();
    }
}
