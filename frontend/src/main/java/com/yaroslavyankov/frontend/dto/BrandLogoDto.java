package com.yaroslavyankov.frontend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BrandLogoDto {
    private String logo;
    private String logoColor;
    private String textColor;

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
