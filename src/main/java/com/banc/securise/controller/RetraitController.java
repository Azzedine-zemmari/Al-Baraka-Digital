package com.banc.securise.controller;

import com.banc.securise.Dto.DepositeDto;
import com.banc.securise.service.retrait.RetraitService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/retrait")
@AllArgsConstructor
public class RetraitController {
    private RetraitService retraitService;

    @PostMapping(value="/")
    public ResponseEntity<String> Retrait(@RequestPart DepositeDto dto , @RequestPart(value="justificatif" , required = false) MultipartFile justificatif , Authentication authentication){
        try{
            String email = authentication.getName();
            retraitService.createRetrait(dto,justificatif,email);
            return ResponseEntity.ok("retrait avec success");
        }catch(Exception e){
            return ResponseEntity.ok(e.getMessage());
        }
    }
    @PostMapping(value="/active")
    public ResponseEntity<String> confirmRetrait(@RequestParam("id") int id){
        String result = retraitService.confirmRetrait(id);
        return ResponseEntity.ok(result);
    }
    @PostMapping(value="/cancel")
    public ResponseEntity<String> cancelDeposit(@RequestParam("id") int id){
        String result = retraitService.rejectRetrait(id);
        return ResponseEntity.ok(result);
    }
}
