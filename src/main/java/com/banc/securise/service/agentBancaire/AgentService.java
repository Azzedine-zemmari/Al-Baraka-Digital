package com.banc.securise.service.agentBancaire;

import com.banc.securise.Dto.OperationDto;

import java.util.List;

public interface AgentService {
    List<OperationDto> consulterOperationPending();
    List<OperationDto> showAllOperation();
}
