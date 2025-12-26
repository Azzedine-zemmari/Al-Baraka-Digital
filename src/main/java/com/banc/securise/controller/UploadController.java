package com.banc.securise.controller;

import com.banc.securise.service.document.UploadocumentService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "/api/client/operations/")
@AllArgsConstructor
public class UploadController {
    private UploadocumentService uploadocumentService;

    @PostMapping(value="/{id}/document",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(@PathVariable int id , @RequestPart(value="justificatif") MultipartFile justificatif){
        try{
            uploadocumentService.uploadFile(id,justificatif);
            return ResponseEntity.ok("file uploaded successfully");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
