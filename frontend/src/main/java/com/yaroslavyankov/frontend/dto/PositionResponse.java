package com.yaroslavyankov.frontend.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PositionResponse {

    private String instrumentType;

    private Asset asset;

    private BigDecimal averagePositionPrice;

    private BigDecimal expectedYield;

    private BigDecimal currentPrice;

    private String currency;

    private int quantity;

    private BigDecimal totalPrice;

}
