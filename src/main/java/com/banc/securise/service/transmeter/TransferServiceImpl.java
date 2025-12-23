package com.banc.securise.service.transmeter;

import com.banc.securise.Dto.TransferDto;
import com.banc.securise.entity.Account;
import com.banc.securise.entity.Operation;
import com.banc.securise.entity.User;
import com.banc.securise.enums.OperationStatus;
import com.banc.securise.exception.AccountInactiveException;
import com.banc.securise.mapper.TransferMapper;
import com.banc.securise.repository.AccountRepository;
import com.banc.securise.repository.OperationRepository;
import com.banc.securise.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class TransferServiceImpl implements TransferService {
    private final OperationRepository operationRepository;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final TransferMapper transferMapper;


    @Override
    public void createTransfer(TransferDto dto, String email){
        User user = userRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("user not found"));
        Account accountSource = accountRepository.findByOwner(user).orElseThrow(()-> new RuntimeException("account not found"));
        if(user.getActive().equals("false")){
            throw new AccountInactiveException();
        }
        if (dto.getAmount() <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        Operation operation = transferMapper.toEntity(dto);
        Account accountDestination  = accountRepository.findById(dto.getDestinationAccountId()).orElseThrow(()-> new RuntimeException("account destination not found"));
        if(operation.getAmount() <= 10000){
            if(accountSource.getBalance() >= dto.getAmount()){
                operation.setStatus(OperationStatus.APPROVE);
                operation.setAccountDestination(accountDestination);
                operation.setCreatedAt(LocalDateTime.now());
                operation.setExecutedAt(LocalDateTime.now());
                operation.setValidatedAt(LocalDateTime.now());
                accountSource.setBalance(accountSource.getBalance() - dto.getAmount());
                accountDestination.setBalance(accountDestination.getBalance() + dto.getAmount());
                accountRepository.save(accountSource);
                accountRepository.save(accountDestination);
                operationRepository.save(operation);
            }
        }
    }
}
