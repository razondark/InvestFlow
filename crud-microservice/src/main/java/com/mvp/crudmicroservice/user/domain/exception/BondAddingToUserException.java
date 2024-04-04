package com.mvp.crudmicroservice.user.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Не удалось добавить облигацию пользователю")
public class BondAddingToUserException extends RuntimeException {
    public BondAddingToUserException(String message) {
        super(message);
    }
}
