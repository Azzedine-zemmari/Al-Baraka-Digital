package com.banc.securise.controller;

import com.banc.securise.Dto.DepositeDto;
import com.banc.securise.service.deposite.DepositService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/deposite")
@AllArgsConstructor
@PreAuthorize("hasRole('CLIENT')")
public class DepositeController {
    private DepositService depositService;

    @PostMapping(value="/")
    public ResponseEntity<?> deposit(@RequestBody DepositeDto dto , Authentication authentication){
            String email =  authentication.getName();
            depositService.createDeposit(dto, email);
            return ResponseEntity.ok("deposite success");
    }
    @PostMapping(value="/active")
    public ResponseEntity<String> confirmDeposit(@RequestParam("id") int id){
        String result = depositService.confirmDeposit(id);
        return ResponseEntity.ok(result);
    }
    @PostMapping(value="/cancel")
    public ResponseEntity<String> cancelDeposit(@RequestParam("id") int id){
        String result = depositService.rejectDeposit(id);
        return ResponseEntity.ok(result);
    }
}
