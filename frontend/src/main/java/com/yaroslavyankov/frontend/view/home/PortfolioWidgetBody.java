package com.yaroslavyankov.frontend.view.home;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PortfolioWidgetBody {

    private BigDecimal expectedYield;

    private BigDecimal totalAmountPortfolio;

    private int positionCount;

}
