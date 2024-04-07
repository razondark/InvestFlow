package com.yaroslavyankov.frontend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Сущность валюты")
@Data
public class CurrencyDto extends Asset {
    @Schema(description = "Название инструмента")
    private String name;

    @Schema(description = "Валюта расчётов")
    private String currency;

    @Schema(description = "Figi-идентификатор инструмента")
    private String figi;

    @Schema(description = "Тикер инструмента")
    private String ticker;

    @Schema(description = "Наименование страны риска (страны, в которой компания ведёт основной бизнес)")
    private String countryOfRiskName;

    @Schema(description = "Сущность информации о логотипе")
    private BrandLogoDto brandLogo;
}
