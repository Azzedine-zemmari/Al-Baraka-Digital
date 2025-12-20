package com.banc.securise.controller;

import com.banc.securise.Dto.DepositeDto;
import com.banc.securise.service.deposite.DepositService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/deposite")
@AllArgsConstructor
@PreAuthorize("hasRole('CLIENT')")
public class DepositeController {
    private DepositService depositService;

    @PostMapping("/")
    public ResponseEntity<?> deposit(@RequestBody DepositeDto dto , Authentication authentication){
        String email = (String) authentication.getPrincipal();
        depositService.createDeposit(dto, email);
        return ResponseEntity.ok("deposite success");
    }
}
