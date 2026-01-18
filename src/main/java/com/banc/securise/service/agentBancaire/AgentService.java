package com.banc.securise.service.agentBancaire;

import com.banc.securise.Dto.OperationDto;
import com.banc.securise.entity.Operation;
import com.banc.securise.entity.Document;

import java.util.List;

public interface AgentService {
    List<OperationDto> consulterOperationPending();
    List<OperationDto> showAllOperation();
    Operation getOperationById(int id);
    List<Document> showAllDocuments();
}
