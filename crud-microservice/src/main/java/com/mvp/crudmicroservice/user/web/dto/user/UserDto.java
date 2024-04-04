package com.mvp.crudmicroservice.user.web.dto.user;

import com.mvp.crudmicroservice.user.domain.user.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Set;

@Schema(description = "Сущность пользователя")
@Data
public class UserDto {

    @Schema(description = "Идентификатор")
    private Long id;

    @Schema(description = "Имя пользователя")
    private String name;

    @Schema(description = "Логин/Почта пользователя")
    private String username;

    @Schema(description = "Пароль пользователя")
    private String password;

    @Schema(description = "Роль пользователя")
    private Set<Role> roles;

}
