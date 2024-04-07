package com.mvp.investservice.web.dto;

import com.mvp.investservice.web.dto.user.UserDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * дтошка для создания аккаунта в инвестициях
 * и привязки его к ползователю приложения
 */

@Schema(description = "Сущность аккаунта")
@Data
public class AccountDto {
    @Schema(description = "ID аккаунта в API")
    private String investAccountId;

    @Schema(description = "Сущность пользователя")
    private UserDto userDto;

    // TODO: add telegramDto
}
