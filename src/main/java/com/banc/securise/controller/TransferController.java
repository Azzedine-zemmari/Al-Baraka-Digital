package com.banc.securise.controller;

import com.banc.securise.Dto.TransferDto;
import com.banc.securise.service.transmeter.TransferService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/transfer")
public class TransferController {
    private final TransferService transferService;

    @PostMapping(value="/")
    public ResponseEntity<String> createTransfer(@RequestPart TransferDto dto , @RequestPart(value="justificatif" , required = false) MultipartFile justificatif , Authentication authentication)  {
        try{
            String email = authentication.getName();
            transferService.createTransfer(dto,justificatif,email);
            return ResponseEntity.ok("success");
        }catch(IOException e){
            return ResponseEntity.ok(e.getMessage());
        }
    }
    @PostMapping(value="/active")
    public ResponseEntity<String> confirmRetrait(@RequestParam("id") int id){
        String result = transferService.confirmTransfer(id);
        return ResponseEntity.ok(result);
    }
    @PostMapping(value="/cancel")
    public ResponseEntity<String> cancelDeposit(@RequestParam("id") int id){
        String result = transferService.cancelTransfer(id);
        return ResponseEntity.ok(result);
    }
}
