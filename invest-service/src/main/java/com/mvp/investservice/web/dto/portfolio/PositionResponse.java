package com.mvp.investservice.web.dto.portfolio;

import com.mvp.investservice.web.dto.Asset;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "Сущность активов портфеля")
@Data
public class PositionResponse {
    @Schema(description = "Тип инструмента (акция/облигация итд)")
    private String instrumentType;

    @Schema(description = "Сущность актива")
    private Asset asset;

    @Schema(description = "Средняя цена активов")
    private BigDecimal averagePositionPrice;

    @Schema(description = "Ожидаемая доходность")
    private BigDecimal expectedYield;

    @Schema(description = "Текущая цена")
    private BigDecimal currentPrice;

    @Schema(description = "Валюта")
    private String currency;

    @Schema(description = "Количество")
    private int quantity;

    @Schema(description = "Общая стоимость")
    private BigDecimal totalPrice;
}
