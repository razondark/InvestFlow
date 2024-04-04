package com.mvp.investservice.web.dto.stock;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Schema(description = "Сущность информации о дивидендах акции")
@AllArgsConstructor
@Data
public class DividendResponse {
    @Schema(description = "Figi-идентификатор инструмента")
    String figi;

    @Schema(description = "Сущность дивидендов акции")
    List<DividendDto> dividends;
}
