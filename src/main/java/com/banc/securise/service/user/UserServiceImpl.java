package com.banc.securise.service.user;

import com.banc.securise.Dto.AuthResponse;
import com.banc.securise.Dto.UserDto;
import com.banc.securise.Dto.UserLoginDto;
import com.banc.securise.Dto.UserRegisterDto;
import com.banc.securise.entity.User;
import com.banc.securise.mapper.UserDtoMapper;
import com.banc.securise.mapper.UserMapper;
import com.banc.securise.repository.UserRepository;
import com.banc.securise.security.JwtService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserDtoMapper userDtoMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;


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
    public AuthResponse loginUser(@NotNull UserLoginDto dto){
        // authentication spring security
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getEmail(),
                        dto.getPassword()
                )
        );

        // get authenticated user

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // generate token

        String token = jwtService.generateToken(userDetails);

        // get user entity
        User user = userRepository.findByEmail(dto.getEmail()).orElseThrow(()-> new RuntimeException("user not found"));

        UserDto userDto = userDtoMapper.userToDto(user);

        return new AuthResponse(token,userDto);

    }


}
