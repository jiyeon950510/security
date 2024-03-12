package com.jycoding.login.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Setter
@Getter
@ToString
@Builder
public class User {
    private Integer id;
    private String username;
    private String password;
    private String role;
    private String email;
    private Timestamp createdAt;

}
