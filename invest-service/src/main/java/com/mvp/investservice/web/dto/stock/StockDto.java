package com.mvp.investservice.web.dto.stock;

import com.mvp.investservice.web.dto.Asset;
import com.mvp.investservice.web.dto.BrandLogoDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "Сущность купона акции")
@Data
public class StockDto extends Asset {
    @Schema(description = "Тикер инструмента")
    private String ticker;

    @Schema(description = "Figi-идентификатор инструмента")
    private String figi;

    @Schema(description = "Название инструмента")
    private String name;

    @Schema(description = "Сектор экономики")
    private String sector;

    @Schema(description = "Валюта расчётов")
    private String currency;

    @Schema(description = "Наименование страны риска (страны, в которой компания ведёт основной бизнес)")
    private String countryOfRiskName;

    @Schema(description = "Лотность инструмента")
    private int lots;

    @Schema(description = "Текущая цена")
    private BigDecimal price;

    @Schema(description = "Сущность информации о логотипе")
    private BrandLogoDto brandLogo;
}
