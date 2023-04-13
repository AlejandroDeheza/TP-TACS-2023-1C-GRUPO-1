package com.tacs.backend.controller;

import com.tacs.backend.dto.ExceptionResponse;
import com.tacs.backend.exception.UsernameAlreadyExistsException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestControllerAdvice
public class AuthenticationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({AuthenticationException.class, UsernameAlreadyExistsException.class})
    public final ResponseEntity<Object> handleEntityNotFoundException(Exception ex, WebRequest request){
        ExceptionResponse exception = new ExceptionResponse();
        exception.setTimestamp(new Date());
        exception.setMessage(ex.getLocalizedMessage());
        exception.setDetails(request.getDescription(false));

        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ExceptionResponse exception = new ExceptionResponse();
        exception.setTimestamp(new Date());
        exception.setMessage(ex.getLocalizedMessage());
        exception.setDetails(request.getDescription(false));

        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }
}
