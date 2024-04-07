package com.example.reportservice.model;

import lombok.Data;

import java.util.List;

@Data
public class OperationResponse {
    private List<Operation> operations;
}
