package com.banc.securise.service.handleDocument;

import com.banc.securise.entity.Account;
import com.banc.securise.entity.Operation;
import com.banc.securise.enums.OperationStatus;
import com.banc.securise.enums.OperationType;
import com.banc.securise.repository.AccountRepository;
import com.banc.securise.repository.DocumentRepository;
import com.banc.securise.repository.OperationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class HandleDocumentServiceImpl implements HandleDocumentService{
    private AccountRepository accountRepository;
    private OperationRepository operationRepository;

    @Override
    public String confirmDocument(int id){
        Operation op = operationRepository.findById(id).orElseThrow(()-> new RuntimeException("operation not found"));
        if(op.getStatus().equals(OperationStatus.PENDING)) {
            op.setStatus(OperationStatus.APPROVE);
            op.setValidatedAt(LocalDateTime.now());
            op.setExecutedAt(LocalDateTime.now());
            operationRepository.save(op);
            switch(op.getType()) {
                case DEPOSIT -> {
                    Account account = op.getAccountDestination();
                    account.setBalance(account.getBalance() + op.getAmount());
                    accountRepository.save(account);
                }
                case WITHDRAWAL -> {
                    Account account = op.getAccountSource();
                    account.setBalance(account.getBalance() - op.getAmount());
                    accountRepository.save(account);
                }
                case TRANSFER -> {
                    Account accountSource = op.getAccountSource();
                    Account accountDestination = op.getAccountDestination();
                    accountSource.setBalance(accountSource.getBalance() - op.getAmount());
                    accountDestination.setBalance(accountDestination.getBalance() + op.getAmount());
                    accountRepository.save(accountSource);
                    accountRepository.save(accountDestination);
                }
                default -> throw new RuntimeException("Unsupported operation type");
            }
        }
            return "Operation confirmed";
    }
    @Override
    public String rejectDocument(int id){
        Operation op = operationRepository.findById(id).orElseThrow(()-> new RuntimeException("operation not found"));
        if(op.getStatus().equals(OperationStatus.PENDING)){
            op.setStatus(OperationStatus.CANCELED);
            op.setValidatedAt(LocalDateTime.now());
            op.setExecutedAt(LocalDateTime.now());
            operationRepository.save(op);
            return "Operation canceled";
        }
        return "Operation is already canceld or accepted";
    }

}
