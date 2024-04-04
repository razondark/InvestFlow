package com.mvp.investservice.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Дтошка, возвращающая баланс аккаунта
 * и сумму пополнения
 */

@Schema(description = "Сущность пополненного баланса")
@Data
public class BalanceDto {
    @Schema(description = "Баланс")
    private BigDecimal balance;

    @Schema(description = "Сумма пополнения")
    private BigDecimal addedMoney;
}
