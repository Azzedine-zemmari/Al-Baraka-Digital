package com.banc.securise.mapper;

import com.banc.securise.Dto.UserRegisterDto;
import com.banc.securise.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserRegisterDto userToDto(User user);
    User userRegisterToUser(UserRegisterDto dto);
}
