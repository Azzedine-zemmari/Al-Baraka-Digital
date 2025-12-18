package com.banc.securise.mapper;

import com.banc.securise.Dto.UserDto;
import com.banc.securise.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {
    User dtoToUser(UserDto dto);
    UserDto userToDto(User user);
}
