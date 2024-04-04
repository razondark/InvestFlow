package com.yaroslavyankov.frontend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Сущность продажи актива")
@Data
public class SaleRequest {
    @Schema(description = "ID аккаунта в API")
    private String accountId;

    @Schema(description = "Figi-идентификатор инструмента")
    private String figi;

    @Schema(description = "Количество лотов")
    private int lot;

    @Schema(description = "Сущность типа заявки")
    private OrderType orderType;
}
