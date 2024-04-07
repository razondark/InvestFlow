package com.yaroslavyankov.frontend.dto.user;

import lombok.Data;

import java.util.Set;

@Data
public class UserDto {

    private Long id;

    private String name;

    private String username;

    private String password;

}
