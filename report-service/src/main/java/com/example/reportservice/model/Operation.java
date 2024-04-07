package com.example.reportservice.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Operation {

    private String operationId;

    private String instrumentType;

    private String assetName;

    private String figi;

    private String currency;

    private String operationType;

    private String operationState;

    private int quantity;

    private BigDecimal payment;

    private BigDecimal instrumentPrice;

    private BigDecimal lotPrice;

    private LocalDateTime operationDate;
}
