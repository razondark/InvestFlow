package com.mvp.investservice.web.controller;

import com.mvp.investservice.service.AccountService;
import com.mvp.investservice.web.dto.AccountDto;
import com.mvp.investservice.web.dto.BalanceDto;
import com.mvp.investservice.web.dto.PayInDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("api/v1/invest/accounts")
@RequiredArgsConstructor
@Tag(name="AccountController", description="Работа с аккаунтом в API")
public class AccountController {

    private final AccountService accountService;

    @Operation(
            summary = "Открытие аккаунта в API",
            description = "Создает аккаунт для пользователя в API"
    )
    @PutMapping("/{userId}")
    public AccountDto openAccount(@Parameter(description = "ID пользователя") @PathVariable Long userId) {
        return accountService.openAccount(userId);
    }

    @Operation(
            summary = "Изменение баланса пользователя в API",
            description = "Изменяет баланс пользователя в API"
    )
    @PutMapping()
    public BalanceDto payIn(@Parameter(description = "Сущность пополнения баланса") @RequestBody PayInDto payInDto) {
        return accountService.payIn(payInDto);
    }

    // TODO: change
    @Operation(
            summary = "Получение API аккаунта пользователя (HARDCODE NOW)",
            description = "Возвращает сущность аккаунта"
    )
    @GetMapping
    public AccountDto getAccount() {
        return accountService.getAccount();
    }
}
