package com.mvp.investservice.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.tinkoff.piapi.contract.v1.OrderDirection;

@Schema(description = "Сущность продажи актива")
@Data
public class SaleDto {
    @Schema(description = "ID аккаунта в API")
    private String accountId;

    @Schema(description = "Figi-идентификатор инструмента")
    private String figi;

    @Schema(description = "Количество лотов")
    private int lot;

    @Schema(description = "Сущность типа заявки")
    private OrderType orderType;
}
