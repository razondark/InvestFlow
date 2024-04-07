package com.mvp.crudmicroservice.user.web.controller;

import com.mvp.crudmicroservice.user.domain.exception.ResourceNotFoundException;
import com.mvp.crudmicroservice.user.domain.user.Account;
import com.mvp.crudmicroservice.user.service.AccountService;
import com.mvp.crudmicroservice.user.web.dto.user.AccountDto;
import com.mvp.crudmicroservice.user.web.mapper.AccountMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/crud/accounts")
@RequiredArgsConstructor
@Tag(name="AccountController", description="Работа с аккаунтом в API")
public class AccountController {

    private final AccountService accountService;

    private final AccountMapper accountMapper;

    @Operation(
            summary = "Обновление аккаунта пользователя???",
            description = "???"
    )
    @PutMapping
    public ResponseEntity<AccountDto> update(@Parameter(description = "Сущность аккаунта") @RequestBody AccountDto accountDto) {
        Account account = accountMapper.toEntity(accountDto);
        Account updatedAccount = accountService.update(account);

        return ResponseEntity
                .ok()
                .body(accountMapper.toDto(account));
    }


    @Operation(
            summary = "Получение аккаунта по ID???",
            description = "???"
    )
    @GetMapping("/{userId}")
    public ResponseEntity getAccountByUserId(@Parameter(description = "ID аккаунта") @PathVariable Long userId) {
        try {
            Account account = accountService.getByUserId(userId);
            return ResponseEntity
                    .ok()
                    .body(accountMapper.toDto(account));
        } catch (ResourceNotFoundException exception) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .header("error-message", exception.getMessage())
                    .body(exception.getMessage());
        }
    }

}
