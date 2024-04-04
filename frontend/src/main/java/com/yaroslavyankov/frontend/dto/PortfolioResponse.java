package com.yaroslavyankov.frontend.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class PortfolioResponse {

    private String accountId;

    private BigDecimal expectedYield;

    private BigDecimal totalAmountShares;

    private BigDecimal totalAmountBonds;

    private BigDecimal totalAmountCurrencies;

    private BigDecimal totalAmountPortfolio;

    List<PositionResponse> positions;
}
