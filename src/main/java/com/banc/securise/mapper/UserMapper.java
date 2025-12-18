package com.banc.securise.mapper;

import com.banc.securise.Dto.UserRegisterDto;
import com.banc.securise.entity.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserRegisterDto userToDto(User user) {
        if(user == null) return null;

        UserRegisterDto dto = new UserRegisterDto();
        dto.setEmail(user.getEmail());
        dto.setFullName(user.getFullName());
        return dto;
    }

    // UserRegisterDto -> User
    public User userRegisterToUser(UserRegisterDto dto) {
        if(dto == null) return null;

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setFullName(dto.getFullName());
        return user;
    }
}
