package com.yaroslavyankov.frontend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "Сущность операции")
@Data
public class Operation {
    @Schema(description = "Идентификатор операции")
    private String operationId;

    @Schema(description = "Тип инструмента (только bond, share, currency, etf, futures)")
    private String instrumentType;

    @Schema(description = "Название актива")
    private String assetName;

    @Schema(description = "Figi-идентификатор инструмента, связанного с операцией")
    private String figi;

    @Schema(description = "Валюта операции")
    private String currency;

    @Schema(description = "Тип операции")
    private String operationType;

    @Schema(description = "Состояние операции")
    private String operationState;

    @Schema(description = "Количество единиц инструмента")
    private int quantity;

    @Schema(description = "Сумма операции")
    private BigDecimal payment;

    @Schema(description = "Цена инструмента")
    private BigDecimal instrumentPrice;

    @Schema(description = "Цена лотов")
    private BigDecimal lotPrice;

    @Schema(description = "Дата операции")
    private LocalDateTime operationDate;
}
