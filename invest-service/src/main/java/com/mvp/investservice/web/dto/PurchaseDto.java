package com.mvp.investservice.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Покупка актива
 */

@Schema(description = "Сущность покупки актива")
@Data
public class PurchaseDto {
    @Schema(description = "ID аккаунта в API")
    private String accountId;

    @Schema(description = "Figi-идентификатор инструмента")
    private String figi;

    @Schema(description = "Количество лотов")
    private int lot;

    @Schema(description = "Сущность типа заявки")
    private OrderType orderType;
}
