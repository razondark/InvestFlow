package com.mvp.investservice.web.dto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(description = "Сущность информации о логотипе актива")
@Data
@AllArgsConstructor
public class BrandLogoDto {
    @Schema(description = "Имя логотипа формата <logoName.extension>")
    private String logo;

    @Schema(description = "Цвет фона логотипа")
    private String logoColor;

    @Schema(description = "Цвет текста на фоне логотипа")
    private String textColor;

    @Operation(
            summary = "Получение ссылки на логотип",
            description = "Возвращает ссылку на логотип в формате <https://invest-brands.cdn-tinkoff.ru/{logoName}x320.{extension}}>"
    )
    public String getLogoUrl() {
        var temp = logo.split("\\.");
        var logoName = temp[0]; // [figi]
        var logoExtencion = "." + temp[1]; // [.png]

        return new StringBuilder()
                .append("https://invest-brands.cdn-tinkoff.ru/")
                .append(logoName)
                .append("x320")
                .append(logoExtencion)
                .toString();
    }
}
