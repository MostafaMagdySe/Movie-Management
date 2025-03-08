package com.movies_management.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler  {



    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception exception) {

        String error = "Error: " + exception.getMessage() + ". Please contact support if the issue persists.";
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
