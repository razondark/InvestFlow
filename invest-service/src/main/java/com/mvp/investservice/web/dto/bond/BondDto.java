package com.mvp.investservice.web.dto.bond;

import com.mvp.investservice.web.dto.Asset;
import com.mvp.investservice.web.dto.BrandLogoDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "Сущность облигации")
@Data
public class BondDto extends Asset {
    @Schema(description = "Тикер инструмента")
    private String ticker;

    @Schema(description = "Figi-идентификатор инструмента")
    private String figi;

    @Schema(description = "Название инструмента")
    private String name;

    @Schema(description = "Лотность инструмента")
    private int lots;

    @Schema(description = "Валюта расчётов")
    private String currency;

    @Schema(description = "Валюта расчётов")
    private int couponQuantityPerYear;

    @Schema(description = "Дата погашения облигации")
    private LocalDateTime maturityDate;

    @Schema(description = "Дата размещения")
    private BigDecimal placementPrice;

    @Schema(description = "Наименование страны риска (страны, в которой компания ведёт основной бизнес)")
    private String countryOfRiskName;

    @Schema(description = "Сектор экономики")
    private String sector;

    @Schema(description = "Уровень риска")
    private RiskLevel riskLevel;

    @Schema(description = "Текущая цена")
    private BigDecimal price;

    @Schema(description = "Сущность информации о логотипе")
    private BrandLogoDto brandLogo;
}
