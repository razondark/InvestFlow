package com.yaroslavyankov.frontend;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BalanceResponse {
    private BigDecimal balance;
    private BigDecimal addedMoney;
}
