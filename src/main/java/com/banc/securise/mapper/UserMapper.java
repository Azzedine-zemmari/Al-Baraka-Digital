package com.banc.securise.mapper;

import com.banc.securise.Dto.UserRegisterDto;
import com.banc.securise.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserRegisterDto userToDto(User user);
    @Mapping(target = "active", ignore = true)
    User userRegisterToUser(UserRegisterDto dto);
}
