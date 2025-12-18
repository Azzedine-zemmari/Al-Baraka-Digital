package com.banc.securise.controller;

import com.banc.securise.Dto.UserRegisterDto;
import com.banc.securise.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthentificationController {
    private final UserService userService;
    
    @PostMapping("/register")
    public ResponseEntity<UserRegisterDto> register(@RequestBody UserRegisterDto dto){
        UserRegisterDto result = userService.registerUser(dto);
        return ResponseEntity.ok(result);
    }
}
