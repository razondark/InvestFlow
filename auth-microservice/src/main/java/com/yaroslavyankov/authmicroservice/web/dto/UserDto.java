package com.yaroslavyankov.authmicroservice.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yaroslavyankov.authmicroservice.domain.user.Role;
import lombok.Data;

import java.util.Set;

@Data
public class UserDto {

    private Long id;

    private String name;

    private String username;

    private String password;

    private Set<Role> roles;

}
