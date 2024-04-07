package com.mvp.investservice.web.dto.bond;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "Сущность купона облигации")
@Data
public class CouponDto {
    @Schema(description = "Дата выплаты купона")
    private LocalDateTime couponDate;

    @Schema(description = "НКД (накопленный купонный доход)")
    private BigDecimal accumulatedCouponIncome;

    @Schema(description = "Номер купона")
    private Long couponNumber;

    @Schema(description = "Размер купона")
    private BigDecimal payment;

    @Schema(description = "Валюта купона")
    private String currency;

    @Schema(description = "Процентный доход купона")
    private BigDecimal interestIncome; // процентный доход
}
