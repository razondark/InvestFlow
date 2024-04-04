package com.mvp.investservice.web.dto.bond;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Уровень риска облигации")
public enum RiskLevel {

    @Schema(description = "Не указан")
    RISK_LEVEL_UNSPECIFIED,

    @Schema(description = "Низкий уровень риска")
    RISK_LEVEL_LOW,

    @Schema(description = "Средний уровень риска")
    RISK_LEVEL_MODERATE,

    @Schema(description = "Высокий уровень риска")
    RISK_LEVEL_HIGH
}
