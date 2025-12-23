package com.banc.securise.controller;

import com.banc.securise.Dto.DepositeDto;
import com.banc.securise.service.retrait.RetraitService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/retrait")
@AllArgsConstructor
public class RetraitController {
    private RetraitService retraitService;

    @PostMapping(value="/")
    public ResponseEntity<String> Retrait(@RequestBody DepositeDto dto , Authentication authentication){
        try{
            String email = authentication.getName();
            retraitService.createRetrait(dto,email);
            return ResponseEntity.ok("retrait avec success");
        }catch(Exception e){
            return ResponseEntity.ok(e.getMessage());
        }
    }
}
