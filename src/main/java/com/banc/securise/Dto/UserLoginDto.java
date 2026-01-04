package com.banc.securise.Dto;

import lombok.Data;

@Data
public class UserLoginDto {
    private String email;
    private String password;
    private boolean rememberMe;

}
