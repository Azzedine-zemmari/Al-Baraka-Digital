package com.banc.securise.service.user;

import com.banc.securise.Dto.AuthResponse;
import com.banc.securise.Dto.UserDto;
import com.banc.securise.Dto.UserLoginDto;
import com.banc.securise.Dto.UserRegisterDto;
import com.banc.securise.entity.Account;
import com.banc.securise.entity.Operation;
import com.banc.securise.entity.User;
import com.banc.securise.enums.Role;
import com.banc.securise.mapper.UserDtoMapper;
import com.banc.securise.mapper.UserMapper;
import com.banc.securise.repository.AccountRepository;
import com.banc.securise.repository.OperationRepository;
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
import com.banc.securise.Dto.UserAuthenticatedResponse;
import com.banc.securise.exception.AccountNotFoundException;
import com.banc.securise.exception.UserNotFoundException;

import java.lang.StackWalker.Option;
import java.util.List;
import java.util.Optional;
import java.util.Random;

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
    private final AccountRepository accountRepository;
    private final OperationRepository operationRepository;


    @Override
    public UserRegisterDto registerUser(UserRegisterDto userRegisterDto){
            User user = userMapper.userRegisterToUser(userRegisterDto);

            Optional<User> isUserExists =userRepository.findByEmail(userRegisterDto.getEmail());
            if(isUserExists.isPresent()){
                throw new RuntimeException("user already exists");
            }

            String hash = passwordEncoder.encode(userRegisterDto.getPassword());
            user.setPassword(hash);
            user.setActive(false);
            user.setRole(userRegisterDto.getRole() != null ? userRegisterDto.getRole() : Role.ROLE_CLIENT);

            userRepository.save(user);

            if(user.getRole() == Role.ROLE_CLIENT){
                Account account = new Account();
                int random = 1000 + new Random().nextInt(9000);
                account.setAccountNumber("ACC_"+random);
                account.setBalance(0.0);
                account.setOwner(user);

                accountRepository.save(account);
            }

            return userMapper.userToDto(user);
    }
    @Override
    public AuthResponse loginUser(UserLoginDto dto){
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

        String token = jwtService.generateToken(userDetails,dto.isRememberMe());

        // get user entity
        User user = userRepository.findByEmail(dto.getEmail()).orElseThrow(()-> new RuntimeException("user not found"));

//        UserDto userDto = userDtoMapper.userToDto(user);

        return new AuthResponse(token);

    }

    @Override
    public UserDto activeUser(Integer id){
        User user = userRepository.findById(id).orElseThrow(()->new RuntimeException("User not found"));
        user.setActive(true);
        userRepository.save(user);
        return userDtoMapper.userToDto(user);
    }

    @Override
    public List<UserDto> showAllUsers(){
        return userRepository.findAll()
                .stream()
                .map(userDtoMapper::userToDto)
                .toList();
    }
    @Override
    public UserRegisterDto registerUserByAdmin(UserRegisterDto userRegisterDto){
        User user = userMapper.userRegisterToUser(userRegisterDto);

        Optional<User> isUserExists = userRepository.findByEmail(userRegisterDto.getEmail());
        if(isUserExists.isPresent()){
            throw new RuntimeException("user already exists");
        }

        String hash = passwordEncoder.encode(userRegisterDto.getPassword());
        user.setPassword(hash);
        user.setActive(true);
        user.setRole(userRegisterDto.getRole() != null ? userRegisterDto.getRole() : Role.ROLE_CLIENT);
        System.out.println("user role : "  + userRegisterDto.getRole());
        userRepository.save(user);
        System.out.println("user active : "  + user.isActive());

        if(user.getRole() == Role.ROLE_CLIENT){
            Account account = new Account();
            int random = 1000 + new Random().nextInt(9000);
            account.setAccountNumber("ACC_"+random);
            account.setBalance(0.0);
            account.setOwner(user);

            accountRepository.save(account);
        }

        return userMapper.userToDto(user);
    }

    @Override
    public String desactiveUser(Integer id){
        User user = userRepository.findById(id).orElseThrow(()->new RuntimeException("User not found"));
        user.setActive(false);
        userRepository.save(user);
        return "user in active successfully";
    }
    @Override
    public UserAuthenticatedResponse getUserAuthenticatedData(String email){
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException());

        Account account = accountRepository.findByOwner(user).orElseThrow(() -> new AccountNotFoundException());

        List<Operation> operations = operationRepository.findOperationsByAccount(account);

        UserAuthenticatedResponse response = new UserAuthenticatedResponse();
        response.setAccountNumber(account.getAccountNumber());
        response.setBalance(account.getBalance());
        response.setOwner(user);
        response.setOperations(operations);

        return response;
    }

}
