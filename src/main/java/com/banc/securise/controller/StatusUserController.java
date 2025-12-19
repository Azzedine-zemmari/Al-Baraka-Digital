package com.banc.securise.controller;

import com.banc.securise.Dto.UserDto;
import com.banc.securise.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/status")
@AllArgsConstructor
public class StatusUserController {
    private final UserService userService;

    @PostMapping("/active/{id}")
    public ResponseEntity<UserDto> activateUser(@PathVariable("id") int id){
        UserDto userDto = userService.activeUser(id);
        return ResponseEntity.ok(userDto);
    }

}
