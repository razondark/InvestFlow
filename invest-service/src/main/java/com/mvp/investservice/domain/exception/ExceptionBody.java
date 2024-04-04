package com.mvp.investservice.domain.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Сущность обработки ошибок")
@Data
public class ExceptionBody {
    @Schema(description = "Сообщение об ошибке")
    private String message;

    public ExceptionBody(String message) {
        this.message = message;
    }
}
