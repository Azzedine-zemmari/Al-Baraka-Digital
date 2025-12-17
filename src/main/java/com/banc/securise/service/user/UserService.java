package com.banc.securise.service.user;

import com.banc.securise.Dto.UserRegisterDto;

public interface UserService {
    UserRegisterDto registerUser(UserRegisterDto userRegisterDto);
}
