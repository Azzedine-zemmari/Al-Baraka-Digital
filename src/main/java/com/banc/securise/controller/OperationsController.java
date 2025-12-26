package com.banc.securise.controller;

import com.banc.securise.Dto.OperationDto;
import com.banc.securise.service.agentBancaire.AgentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/client/operations")
@AllArgsConstructor
public class OperationsController {
    private AgentService agenceService;

    @GetMapping(value="/pending")
    public ResponseEntity<List<OperationDto>> operationPending(){
        List<OperationDto> result = agenceService.consulterOperationPending();
        return ResponseEntity.ok(result);
    }
    @GetMapping(value="/")
    public ResponseEntity<List<OperationDto>> allOperation(){
        List<OperationDto> result = agenceService.showAllOperation();
        return ResponseEntity.ok(result);
    }
}
