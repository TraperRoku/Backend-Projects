package com.TraperRoku.backend.config;


import com.TraperRoku.backend.Dto.ErrorDto;
import com.TraperRoku.backend.exceptions.AppException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice

public class RestExceptionHandler {
    @ExceptionHandler(value = {AppException.class})
    @ResponseBody
    public ResponseEntity<ErrorDto> handleException(AppException exception){
        return ResponseEntity.status(exception.getStatus()).
                body(ErrorDto.builder().message(exception.getMessage()).build());
    }
}
