package com.yaroslavyankov.authmicroservice.web.controller;

import com.yaroslavyankov.authmicroservice.domain.exception.AccessDeniedException;
import com.yaroslavyankov.authmicroservice.domain.exception.ExceptionBody;
import com.yaroslavyankov.authmicroservice.domain.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionBody handleIllegalStateException(IllegalStateException exception) {
        return new ExceptionBody(exception.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionBody handleResourceNotFoundException(ResourceNotFoundException exception) {
        return new ExceptionBody(exception.getMessage());
    }

    @ExceptionHandler({AccessDeniedException.class, org.springframework.security.access.AccessDeniedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionBody handleAccessDenied() {
        return new ExceptionBody("Access denied.");
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionBody handleAuthentication() {
        return new ExceptionBody("Authentication failed.");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionBody handleException(Exception e) {
        return new ExceptionBody("Interval error");
    }
}
