package com.TraperRoku.Restaurant.Review.Platform.config;


import com.TraperRoku.Restaurant.Review.Platform.dto.ErrorDto;
import com.TraperRoku.Restaurant.Review.Platform.exception.AppException;
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