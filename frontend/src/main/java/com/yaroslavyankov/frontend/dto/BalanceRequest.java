package com.yaroslavyankov.frontend.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BalanceRequest {
    private String accountId;
    private BigDecimal moneyToPay;
}
