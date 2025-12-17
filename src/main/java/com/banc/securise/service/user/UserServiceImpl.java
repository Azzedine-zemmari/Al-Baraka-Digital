package com.banc.securise.service.user;

import com.banc.securise.Dto.UserRegisterDto;
import com.banc.securise.entity.User;
import com.banc.securise.mapper.UserMapper;
import com.banc.securise.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;
    private UserMapper userMapper;

    @Override
    public UserRegisterDto registerUser(UserRegisterDto userRegisterDto){

    }


}
