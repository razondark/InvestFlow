package com.mvp.investservice.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Используется для пополнения счёта аккаунта
 */

@Schema(description = "Сущность пополнения баланса")
@Data
public class PayInDto {
    @Schema(description = "ID аккаунта в API")
    private String accountId;

    @Schema(description = "Сумма пополнения")
    private BigDecimal moneyToPay;
}
