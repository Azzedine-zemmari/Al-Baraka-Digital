package com.banc.securise.service.agentBancaire;

import com.banc.securise.Dto.OperationDto;
import com.banc.securise.entity.Document;
import com.banc.securise.entity.Operation;
import com.banc.securise.enums.OperationStatus;
import com.banc.securise.mapper.OperationDtoMaper;
import com.banc.securise.repository.DocumentRepository;
import com.banc.securise.repository.OperationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AgentServiceImpl implements AgentService{
    private OperationRepository operationRepository;
    private OperationDtoMaper operationDtoMaper;
    private DocumentRepository documentRepository;

    @Override
    public List<OperationDto> consulterOperationPending() {
        return operationRepository
                .findByStatus(OperationStatus.PENDING)
                .stream()
                .map(operationDtoMaper::entityToDto)
                .toList();
    }
    @Override
    public List<OperationDto> showAllOperation(){
        return operationRepository
                .findAll()
                .stream()
                .map(operationDtoMaper::entityToDto)
                .toList();
    }
    @Override
    public Operation getOperationById(int id){
        return operationRepository.findById(id).orElseThrow(()-> new RuntimeException("operation not found"));
    }

    @Override
    public List<Document> showAllDocuments(){
        List<Document> result = documentRepository.findAllTheDocumentWithOperationPending();
        return result;
    }
}
