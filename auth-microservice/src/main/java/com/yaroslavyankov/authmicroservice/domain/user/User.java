package com.yaroslavyankov.authmicroservice.domain.user;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class User {

    private Long id;
    private String name;
    private String username;
    private String password;
    private String passwordConfirmation;
    private Set<Role> roles;

}
