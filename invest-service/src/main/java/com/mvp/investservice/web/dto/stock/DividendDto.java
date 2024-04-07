package com.mvp.investservice.web.dto.stock;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "Сущность дивиденда акции")
@Data
public class DividendDto {
    @Schema(description = "Дата выплаты дивидендов")
    private LocalDateTime date;

    @Schema(description = "Величина дивиденда на 1 ценную бумагу")
    private BigDecimal paymentPerShare;

    @Schema(description = "Валюта дивиденда")
    private String currency;

    @Schema(description = "Процентный доход дивиденда")
    private BigDecimal interestIncome;
}
