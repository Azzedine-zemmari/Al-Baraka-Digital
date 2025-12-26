package com.banc.securise.service.user;

import com.banc.securise.Dto.AuthResponse;
import com.banc.securise.Dto.UserDto;
import com.banc.securise.Dto.UserLoginDto;
import com.banc.securise.Dto.UserRegisterDto;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface UserService {
    UserRegisterDto registerUser(UserRegisterDto userRegisterDto);
    AuthResponse loginUser(UserLoginDto dto);
    UserDto activeUser(Integer id);
    List<UserDto> showAllUsers();
    UserRegisterDto registerUserByAdmin(UserRegisterDto userRegisterDto);

}
