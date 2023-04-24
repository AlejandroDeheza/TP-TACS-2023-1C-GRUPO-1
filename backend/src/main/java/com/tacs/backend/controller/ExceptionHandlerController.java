package com.tacs.backend.controller;

import com.tacs.backend.dto.ExceptionResponse;
import com.tacs.backend.exception.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler({UserException.class, EventStatusException.class})
    public final ResponseEntity<Object> handleBadRequestException(Exception ex, WebRequest request){
        ExceptionResponse exception = new ExceptionResponse();
        exception.setTimestamp(new Date());
        exception.setMessage(ex.getLocalizedMessage());
        exception.setDetails(request.getDescription(false));

        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ EntityNotFoundException.class})
    public final ResponseEntity<Object> handleEntityNotFoundException(Exception ex, WebRequest request){
        ExceptionResponse exception = new ExceptionResponse();
        exception.setTimestamp(new Date());
        exception.setMessage(ex.getLocalizedMessage());
        exception.setDetails(request.getDescription(false));

        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({UserIsNotOwnerException.class, AuthenticationException.class, })
    public final ResponseEntity<Object> handleAccessDeniedException(Exception ex, WebRequest request){
        ExceptionResponse exception = new ExceptionResponse();
        exception.setTimestamp(new Date());
        exception.setMessage(ex.getLocalizedMessage());
        exception.setDetails(request.getDescription(false));

        return new ResponseEntity<>(exception, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler({RequestNotAllowException.class})
    public final ResponseEntity<Object> handleTooManyRequest(Exception ex, WebRequest request){
        ExceptionResponse exception = new ExceptionResponse();
        exception.setTimestamp(new Date());
        exception.setMessage(ex.getLocalizedMessage());
        exception.setDetails(request.getDescription(false));

        return new ResponseEntity<>(exception, HttpStatus.TOO_MANY_REQUESTS);
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
