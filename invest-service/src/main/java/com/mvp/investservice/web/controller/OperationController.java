package com.mvp.investservice.web.controller;

import com.mvp.investservice.service.OperationService;
import com.mvp.investservice.web.dto.AccountDto;
import com.mvp.investservice.web.dto.OperationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/invest/operations")
@RequiredArgsConstructor
@Tag(name="OperationController", description="Работа с операциями аккаунта")
public class OperationController {

    private final OperationService operationService;

    @Operation(
            summary = "Получение всех операций по аккаунту",
            description = "Возвращает сущность информации об операциях"
    )
    @PostMapping
    public OperationResponse getAllOperations(@Parameter(description = "Сущность аккаунта") @RequestBody AccountDto accountDto) {
        return operationService.getAllOperations(accountDto);
    }
}
