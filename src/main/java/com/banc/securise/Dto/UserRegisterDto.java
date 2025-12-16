package com.banc.securise.Dto;

import com.banc.securise.enums.Role;
import lombok.Data;

@Data
public class UserRegisterDto {
    private int id;

    private String password;

    private String fullName;

    private Role role;

}
