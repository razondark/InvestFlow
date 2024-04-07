package com.mvp.crudmicroservice.user.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Не удалось добавить акцию пользователю")
public class StockAddingToUserException extends RuntimeException {
    public StockAddingToUserException(String message) {
        super(message);
    }
}
