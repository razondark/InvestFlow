package com.mvp.investservice.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "Сущность информации о заявке")
@Data
public class OrderResponse<A> {
    @Schema(description = "Биржевой идентификатор заявки")
    private String orderId;

    @Schema(description = "Текущий статус заявки")
    private String executionStatus;

    @Schema(description = "Запрошено лотов")
    private int lotRequested;

    @Schema(description = "Исполнено лотов")
    private int lotExecuted;

    @Schema(description = "Сущность актива")
    private A asset;

    @Schema(description = "Начальная цена заявки")
    private BigDecimal initialOrderPrice;

    @Schema(description = "Цена выполненной заявки")
    private BigDecimal executedOrderPrice;

    @Schema(description = "Итоговая стоимость заявки")
    private BigDecimal totalOrderPrice;
}
