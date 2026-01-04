package com.banc.securise.controller;

import com.banc.securise.Dto.AuthResponse;
import com.banc.securise.Dto.UserAuthenticatedResponse;
import com.banc.securise.Dto.UserLoginDto;
import com.banc.securise.Dto.UserRegisterDto;
import com.banc.securise.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
// @CrossOrigin(origins = "http://localhost:4200")
public class AuthentificationController {
    private final UserService userService;
    
    @PostMapping("/register")
    public ResponseEntity<UserRegisterDto> register(@RequestBody UserRegisterDto dto){
        UserRegisterDto result = userService.registerUser(dto);
        return ResponseEntity.ok(result);
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody UserLoginDto dto) {
        return ResponseEntity.ok(userService.loginUser(dto));
    }
    @GetMapping("/testRail")
    public String test(){
        return "Backend running";
    }
    @GetMapping("/userInfo")
    public ResponseEntity<UserAuthenticatedResponse> show(Authentication authentication){
        String email = authentication.getName();
        UserAuthenticatedResponse data = userService.getUserAuthenticatedData(email);
        return ResponseEntity.ok(data);

    }
}
