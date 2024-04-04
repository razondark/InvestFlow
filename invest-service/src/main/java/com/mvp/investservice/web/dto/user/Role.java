package com.mvp.investservice.web.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Сущность роли пользователя")
public enum Role {
    @Schema(description = "Роль пользователя")
    ROLE_USER,

    @Schema(description = "Роль администратора")
    ROLE_ADMIN
}
