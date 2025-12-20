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

    @PostMapping(value="/",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> deposit(@RequestPart DepositeDto dto , @RequestPart(value="justificatif" , required = false) MultipartFile justificatif , Authentication authentication){
        try{
            String email =  authentication.getName();
            depositService.createDeposit(dto,justificatif, email);
            return ResponseEntity.ok("deposite success");
        }catch(IOException e){
            return ResponseEntity.ok(e.getMessage());
        }
    }
}
