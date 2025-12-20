package com.banc.securise.service.deposite;

import com.banc.securise.Dto.DepositeDto;
import com.banc.securise.entity.Account;
import com.banc.securise.entity.Operation;
import com.banc.securise.entity.User;
import com.banc.securise.enums.OperationStatus;
import com.banc.securise.mapper.OperationMapper;
import com.banc.securise.repository.AccountRepository;
import com.banc.securise.repository.OperationRepository;
import com.banc.securise.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class DepositServiceImpl implements DepositService{
    private final OperationRepository operationRepository;
    private final UserRepository userRepository;
    private final OperationMapper operationMapper;
    private final AccountRepository AccountRepository;
    private final AccountRepository accountRepository;

    @Override
    public void createDeposit(DepositeDto depositeDto,String email){
        User user = userRepository.findByEmail(email).orElseThrow(()-> new IllegalStateException("user not authenticated"));
        Account account = accountRepository.findByOwner(user).orElseThrow(()->new IllegalStateException("user has no account"));

        Operation operation = operationMapper.dtoToEntity(depositeDto);

        if(operation.getAmount() <= 10000){
            operation.setStatus(OperationStatus.APPROVE);
            operation.setAccountDestination(account);
            operation.setCreatedAt(LocalDateTime.now());
            operation.setExecutedAt(LocalDateTime.now());
            operation.setValidatedAt(LocalDateTime.now());
        }else{
            operation.setStatus(OperationStatus.PENDING);
        }

        operationRepository.save(operation);

    }
}
