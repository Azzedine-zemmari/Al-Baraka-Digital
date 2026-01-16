package com.banc.securise.controller;

import com.banc.securise.Dto.TransferDto;
import com.banc.securise.service.transmeter.TransferService;
import java.io.IOException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.banc.securise.enums.OperationType;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/transfer")
public class TransferController {

    private final TransferService transferService;

    @PostMapping(value = "/")
    public ResponseEntity<Map<String, String>> createTransfer(
        @RequestBody TransferDto dto,
        Authentication authentication
    ) {
        String email = authentication.getName();
        dto.setType(OperationType.TRANSFER);
        transferService.createTransfer(dto, email);
        return ResponseEntity.ok(Map.of("message", "success"));
    }
}
