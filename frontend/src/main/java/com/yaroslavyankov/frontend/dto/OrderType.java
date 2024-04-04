package com.yaroslavyankov.frontend.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Сущность типа заявки")
public enum OrderType {
    @Schema(description = "Лимитная")
    ORDER_TYPE_LIMIT,

    @Schema(description = "Рыночная")
    ORDER_TYPE_MARKET,

    @Schema(description = "Лучшая цена")
    ORDER_TYPE_BESTPRICE
}
