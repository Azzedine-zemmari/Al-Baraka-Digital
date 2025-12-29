package com.banc.securise.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class debugController {
    @GetMapping("/debug")
    public Object debug(Authentication auth) {
        if (auth == null) {
            return "AUTH IS NULL → JWT NOT PROCESSED";
        }
        return Map.of(
                "principal", auth.getName(),
                "authorities", auth.getAuthorities(),
                "class", auth.getClass().getName()
        );
    }
}
