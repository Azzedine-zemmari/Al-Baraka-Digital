package com.banc.securise.Dto;

import com.banc.securise.enums.Role;
import lombok.Data;

@Data
public class UserRegisterDto {
    private String email;

    private String password;

    private String fullName;
}
