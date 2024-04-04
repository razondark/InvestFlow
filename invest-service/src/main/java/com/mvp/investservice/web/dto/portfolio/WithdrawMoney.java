package com.mvp.investservice.web.dto.portfolio;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "Сущность валют пользователя в портфеле")
@Data
public class WithdrawMoney {
    @Schema(description = "Валюта")
    private String currency;

    @Schema(description = "Сумма")
    private BigDecimal amount;
}
