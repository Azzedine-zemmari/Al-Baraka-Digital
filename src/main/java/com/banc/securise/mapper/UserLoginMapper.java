package com.banc.securise.mapper;

import com.banc.securise.Dto.UserLoginDto;
import com.banc.securise.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserLoginMapper {
    User dtoToUser(UserLoginDto dto);
    UserLoginDto userToDto(User user);
}

