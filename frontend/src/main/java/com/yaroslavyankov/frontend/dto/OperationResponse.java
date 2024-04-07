package com.yaroslavyankov.frontend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "Сущность информации об операциях")
@Data
public class OperationResponse {
    @Schema(description = "Сущность операций")
    private List<Operation> operations;
}
