package com.yaroslavyankov.authmicroservice.web.dto.auth;

import lombok.Data;

@Data
public class RegisteredUser {

    private Long id;

    private String name;

    private String username;
}
