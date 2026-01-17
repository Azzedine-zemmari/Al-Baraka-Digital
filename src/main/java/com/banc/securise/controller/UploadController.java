package com.banc.securise.controller;

import com.banc.securise.service.document.UploadocumentService;

import org.springframework.security.core.Authentication;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import com.banc.securise.entity.Operation;
import lombok.*;


@RestController
@RequestMapping(value = "/api/client/operations")

public class UploadController {
    private final UploadocumentService uploadocumentService;

    public UploadController(UploadocumentService uploadocumentService){
        this.uploadocumentService = uploadocumentService;
    }
    @PostMapping(value="/{id}/document",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(@PathVariable int id , @RequestPart(value="justificatif") MultipartFile justificatif){
        try{
            uploadocumentService.uploadFile(id,justificatif);
            return ResponseEntity.ok("file uploaded successfully");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/unjustified")
    public ResponseEntity<List<Operation>> getOperations(Authentication authentication) {
    String email = authentication.getName();
    List<Operation> operations =
            uploadocumentService.getUnjustifiedOperations(email);

    return ResponseEntity.ok(operations);
}

}
