package com.mvp.investservice.domain.exception;

public class BuyUnavailableException extends RuntimeException {
    public BuyUnavailableException(String message) {
        super(message);
    }
}
