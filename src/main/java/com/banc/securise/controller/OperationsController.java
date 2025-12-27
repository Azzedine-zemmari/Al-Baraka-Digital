package com.banc.securise.controller;

import com.banc.securise.Dto.OperationDto;
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
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.annotation.Documented;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class OperationsController {
    private AgentService agenceService;
    private DocumentRepository documentRepository;

    @GetMapping(value="/agentOauth/pending")
    public ResponseEntity<List<OperationDto>> operationPending(){
        List<OperationDto> result = agenceService.consulterOperationPending();
        return ResponseEntity.ok(result);
    }
    @GetMapping(value="/client/operations")
    public ResponseEntity<List<OperationDto>> allOperation(){
        List<OperationDto> result = agenceService.showAllOperation();
        return ResponseEntity.ok(result);
    }
}
