package com.banc.securise.service.user;

import com.banc.securise.Dto.UserRegisterDto;
import com.banc.securise.entity.User;
import com.banc.securise.mapper.UserMapper;
import com.banc.securise.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;
    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserRegisterDto registerUser(UserRegisterDto userRegisterDto){
            User user = userMapper.userRegisterToUser(userRegisterDto);
            Optional<User> isUserExists =userRepository.findByEmail(user.getEmail());
            if(isUserExists.isPresent()){
                throw new RuntimeException("user already exists");
            }
            String hash = passwordEncoder.encode(userRegisterDto.getPassword());
            user.setPassword(hash);
            userRepository.save(user);
            return userMapper.userToDto(user);
    }


}
