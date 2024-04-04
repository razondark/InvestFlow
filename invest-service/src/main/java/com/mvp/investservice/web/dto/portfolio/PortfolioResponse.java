package com.mvp.investservice.web.dto.portfolio;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Schema(description = "Сущность информации об портфеле пользователя")
@Data
public class PortfolioResponse {
    @Schema(description = "ID аккаунта в API")
    private String accountId;

    @Schema(description = "Ожидаемая доходность")
    private BigDecimal expectedYield;

    @Schema(description = "Общая стоимость акций")
    private BigDecimal totalAmountShares;

    @Schema(description = "Общая стоимость облигаций")
    private BigDecimal totalAmountBonds;

    @Schema(description = "Общая стоимость ценных бумаг (валюты)")
    private BigDecimal totalAmountCurrencies;

    @Schema(description = "Общая стоимость портфеля")
    private BigDecimal totalAmountPortfolio;

    @Schema(description = "Сущность активов портфеля")
    List<PositionResponse> positions;
}
