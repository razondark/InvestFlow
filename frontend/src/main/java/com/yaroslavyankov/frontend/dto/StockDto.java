package com.yaroslavyankov.frontend.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StockDto extends Asset {

    private String ticker;
    private String figi;
    private String name;
    private String sector;
    private String currency;
    private String countryOfRiskName;
    private int lots;

    private BigDecimal price;

    private BrandLogoDto brandLogo;
}
