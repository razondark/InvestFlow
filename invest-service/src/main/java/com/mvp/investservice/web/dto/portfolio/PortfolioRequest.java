package com.mvp.investservice.web.dto.portfolio;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Сущность запроса информации о портфеле пользователя")
@Data
public class PortfolioRequest {
    @Schema(description = "ID аккаунта в API")
    private String accountId;
}
