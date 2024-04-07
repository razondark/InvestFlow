package com.mvp.investservice.web.dto.bond;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Сущность информации о купонах облигации")
@AllArgsConstructor
@Data
public class CouponResponse {
    @Schema(description = "Figi-идентификатор инструмента")
    private String figi;

    @Schema(description = "Дата ближайшей выплаты")
    LocalDateTime nextPaymentDate;

    @Schema(description = "Количество выплаченных купонов")
    Long couponsPaid;

    @Schema(description = "Количество всех купонов")
    Long couponsCount;

    @Schema(description = "Сущность купонов облигации")
    List<CouponDto> coupons;
}
