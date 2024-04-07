package com.yaroslavyankov.authmicroservice.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
public class ExceptionBody {

    private String message;
    public ExceptionBody(String message) {
        this.message = message;
    }
}
