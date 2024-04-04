package com.mvp.crudmicroservice.user.web.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Сущность телеграм аккаунта")
@Data
public class TelegramDto {
    @Schema(description = "Идентификатор")
    private Long id;

    @Schema(description = "Имя (формата @username без @)")
    private String name;

    @Schema(description = "ID телеграм аккаунта")
    private String telegramId;
}
