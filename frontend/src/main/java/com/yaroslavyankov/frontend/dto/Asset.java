package com.yaroslavyankov.frontend.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = StockDto.class, name = "stock"),
        @JsonSubTypes.Type(value = BondDto.class, name = "bond"),
        @JsonSubTypes.Type(value = CurrencyDto.class, name = "currency")
})
public class Asset {

    protected String sector;

    public String getSector() {
        return sector;
    }
}
