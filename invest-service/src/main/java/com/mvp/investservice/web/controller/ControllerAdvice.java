package com.mvp.investservice.web.controller;

import com.mvp.investservice.domain.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionBody handleIllegalStateException(ResourceNotFoundException exception) {
        return new ExceptionBody(exception.getMessage());
    }

    @ExceptionHandler(UpdateException.class)
    public ExceptionBody handleUpdateException(UpdateException exception) {
        return new ExceptionBody(exception.getMessage());
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<?> handleInsufficientFundsException(InsufficientFundsException ex) {
        record ExceptionBody(String message, Object body) {}
        return new ResponseEntity<>(new ExceptionBody(ex.getMessage(), ex.getAssetInfo()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(YieldNotFoundException.class)
    public ExceptionBody handleYieldNotFoundException(YieldNotFoundException exception) {
        return new ExceptionBody(exception.getMessage());
    }

    @ExceptionHandler(CannotProceedApiRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleApiRequestException(CannotProceedApiRequestException exception) {
        return new ExceptionBody(exception.getMessage());
    }

    @ExceptionHandler({BuyUnavailableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleBuyUnavailableException(BuyUnavailableException exception) {
        return new ExceptionBody(exception.getMessage());
    }

    @ExceptionHandler(AssetNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionBody handleAssetNotFoundException(AssetNotFoundException exception) {
        return new ExceptionBody(exception.getMessage());
    }

}
