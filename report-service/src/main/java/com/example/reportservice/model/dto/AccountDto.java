package com.example.reportservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountDto {
    private String investAccountId;
    private UserDto userDto;
}
