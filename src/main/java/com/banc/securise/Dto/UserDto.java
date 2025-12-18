package com.banc.securise.Dto;

import com.banc.securise.enums.Role;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {
    private int id;

    private String email;

    private String fullName;

    private Role role;

    private Boolean active;

    private LocalDateTime created_at;
}
