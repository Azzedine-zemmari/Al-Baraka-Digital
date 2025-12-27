package com.banc.securise.controller;

import com.banc.securise.Dto.UserDto;
import com.banc.securise.Dto.UserRegisterDto;
import com.banc.securise.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@AllArgsConstructor
public class StatusUserController {
    private final UserService userService;

    @PostMapping("/active/{id}")
    public ResponseEntity<UserDto> activateUser(@PathVariable("id") int id){
        UserDto userDto = userService.activeUser(id);
        return ResponseEntity.ok(userDto);
    }
    @GetMapping("/showAll")
    public ResponseEntity< List<UserDto>> showAllUser(){
        List<UserDto> userDtos = userService.showAllUsers();
        return ResponseEntity.ok(userDtos);
    }
    @PostMapping("/createUser")
    public ResponseEntity<UserRegisterDto> registerUserByAdmin(@RequestBody UserRegisterDto dto){
        UserRegisterDto result = userService.registerUserByAdmin(dto);
        return ResponseEntity.ok(result);
    }
    @PostMapping("/desactiveUser/{id}")
    public ResponseEntity<String> desactiveUserByAdmin(@PathVariable int id){
        String result = userService.desactiveUser(id);
        return ResponseEntity.ok(result);
    }

}
