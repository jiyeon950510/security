package com.jycoding.login.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class JoinReqDto {
    private String username;
    private String password;
    private String email;
    private String role;
}
