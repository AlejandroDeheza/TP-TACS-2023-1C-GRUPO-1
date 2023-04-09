package com.tacs.backend.controller;

import com.tacs.backend.dto.ExceptionResponse;
import com.tacs.backend.exception.SessionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class SessionServiceExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({SessionException.class})
    public final ResponseEntity<Object> handleEntityNotFoundException(Exception ex, WebRequest request){
        ExceptionResponse exception = new ExceptionResponse();
        exception.setTimestamp(new Date());
        exception.setMessage(ex.getMessage());
        exception.setDetails(request.getDescription(false));

        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }
}
