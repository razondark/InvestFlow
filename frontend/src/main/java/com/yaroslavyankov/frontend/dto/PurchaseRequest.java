package com.yaroslavyankov.frontend.dto;

import com.yaroslavyankov.frontend.util.OrderTypeUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Сущность покупки актива")
@Data
public class PurchaseRequest {
    @Schema(description = "ID аккаунта в API")
    private String accountId;

    @Schema(description = "Figi-идентификатор инструмента")
    private String figi;

    @Schema(description = "Количество лотов")
    private int lot;

    @Schema(description = "Сущность типа заявки")
    private OrderType orderType;
}
