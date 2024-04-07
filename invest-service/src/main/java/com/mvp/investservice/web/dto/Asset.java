package com.mvp.investservice.web.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.mvp.investservice.web.dto.bond.BondDto;
import com.mvp.investservice.web.dto.stock.StockDto;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Сущность актива")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = StockDto.class, name = "stock"),
        @JsonSubTypes.Type(value = BondDto.class, name = "bond"),
        @JsonSubTypes.Type(value = CurrencyDto.class, name = "currency")
})
public class Asset {
}
