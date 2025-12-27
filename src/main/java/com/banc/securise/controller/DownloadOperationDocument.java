package com.banc.securise.controller;

import com.banc.securise.entity.Document;
import com.banc.securise.entity.Operation;
import com.banc.securise.repository.DocumentRepository;
import com.banc.securise.service.agentBancaire.AgentService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class DownloadOperationDocument {
    private AgentService agenceService;
    private DocumentRepository documentRepository;

    @GetMapping("/agent/operations/{id}/download")
    public ResponseEntity<Resource> downloadDocument(@PathVariable Integer id) throws IOException {
        Operation operation = agenceService.getOperationById(id);
        Document document = documentRepository.findDocumentByOperation_Id(operation.getId());

        if (document == null) {
            return ResponseEntity.notFound().build();
        }

        Path filePath = Paths.get(document.getStoragePath());
        if (!Files.exists(filePath)) {
            return ResponseEntity.notFound().build();
        }

        Resource resource = new UrlResource(filePath.toUri());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/pdf"))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + document.getFileName() + "\"")
                .body(resource);
    }
}
