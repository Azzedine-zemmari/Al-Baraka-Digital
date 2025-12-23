package com.banc.securise.service.retrait;

import com.banc.securise.Dto.DepositeDto;
import com.banc.securise.Dto.RetraitDto;
import com.banc.securise.entity.Account;
import com.banc.securise.entity.Operation;
import com.banc.securise.entity.User;
import com.banc.securise.enums.OperationStatus;
import com.banc.securise.exception.AccountInactiveException;
import com.banc.securise.mapper.OperationMapper;
import com.banc.securise.repository.AccountRepository;
import com.banc.securise.repository.OperationRepository;
import com.banc.securise.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RetraitServiceImpl implements RetraitService{
    private final OperationRepository operationRepository;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final OperationMapper operatinonMapper;

    @Override
    public void createRetrait(DepositeDto dto, String email) throws IOException{
        User user = userRepository.findByEmail(email).orElseThrow(()-> new IllegalStateException("user not authenticated"));
        Account account = accountRepository.findByOwner(user).orElseThrow(()->new IllegalStateException("user has no account"));
        if(user.getActive().equals("false")){
            throw new AccountInactiveException();
        }
        if(account.getBalance() > dto.getAmount()){
            Operation operation = operatinonMapper.dtoToEntity(dto);
            if(operation.getAmount() <= 10000){
                operation.setStatus(OperationStatus.APPROVE);
                operation.setAccountDestination(account);
                operation.setCreatedAt(LocalDateTime.now());
                operation.setExecutedAt(LocalDateTime.now());
                operation.setValidatedAt(LocalDateTime.now());
                account.setBalance(account.getBalance() - operation.getAmount());
                accountRepository.save(account);
            }

            operationRepository.save(operation);
        }

    }
}
