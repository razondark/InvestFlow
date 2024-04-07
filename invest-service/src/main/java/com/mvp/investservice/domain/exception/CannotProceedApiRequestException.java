package com.mvp.investservice.domain.exception;

public class CannotProceedApiRequestException extends RuntimeException {
    public CannotProceedApiRequestException(String s) {
        super(s);
    }
}
