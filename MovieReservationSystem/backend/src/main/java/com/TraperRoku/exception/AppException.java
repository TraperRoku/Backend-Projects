package com.TraperRoku.exception;

import org.springframework.http.HttpStatus;

public class AppException extends RuntimeException{

    private final HttpStatus status;

    public AppException(HttpStatus status, String errorMessage) {
        super(errorMessage);
        this.status = status;
    }

    public HttpStatus getStatus(){
        return status;
    }
}
