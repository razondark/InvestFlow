package com.mvp.crudmicroservice.user.web.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "Сущность аккаунта")
@Data
public class AccountDto {

    @Schema(description = "ID аккаунта в API")
    private String investAccountId;

    @Schema(description = "Сущность пользователя")
    private UserDto userDto;

    @Schema(description = "Сущность телеграм аккаунта")
    private TelegramDto telegramDto;
}
