package com.TraperRoku.config;





import com.TraperRoku.dto.ErrorDto;
import com.TraperRoku.exception.AppException;
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
                body(ErrorDto.builder().errorMessage(exception.getMessage()).build());
    }
}
