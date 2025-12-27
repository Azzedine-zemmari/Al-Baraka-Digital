package com.banc.securise.controller;

import com.banc.securise.service.handleDocument.HandleDocumentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
@RequestMapping("/api/handle/document")
public class HandleDocumentController {
    private HandleDocumentService handleDocumentService;

    @PostMapping(value="/{id}/confirm")
    public ResponseEntity<String> confirmDocument(@PathVariable int id){
        String confrimation = handleDocumentService.confirmDocument(id);
        return ResponseEntity.ok(confrimation);
    }
}
