package com.mvp.investservice.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class YieldNotFoundException extends RuntimeException {
    public YieldNotFoundException(String message) {
        super(message);
    }
}