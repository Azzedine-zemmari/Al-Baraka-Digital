package com.banc.securise.service.deposite;

import com.banc.securise.Dto.DepositeDto;
import com.banc.securise.entity.Account;
import com.banc.securise.entity.Document;
import com.banc.securise.entity.Operation;
import com.banc.securise.entity.User;
import com.banc.securise.enums.OperationStatus;
import com.banc.securise.enums.OperationType;
import com.banc.securise.exception.AccountInactiveException;
import com.banc.securise.exception.AccountNotFoundException;
import com.banc.securise.exception.UserNotFoundException;
import com.banc.securise.mapper.OperationMapper;
import com.banc.securise.repository.AccountRepository;
import com.banc.securise.repository.DocumentRepository;
import com.banc.securise.repository.OperationRepository;
import com.banc.securise.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class DepositServiceImpl implements DepositService{
    private final OperationRepository operationRepository;
    private final UserRepository userRepository;
    private final OperationMapper operationMapper;
    private final AccountRepository accountRepository;
    private final DocumentRepository documentRepository;
    @Value("${app.upload.dir}")
    private String uploadDir;

    public DepositServiceImpl(OperationRepository operationRepository,
                              UserRepository userRepository,
                              OperationMapper operationMapper,
                              AccountRepository accountRepository,
                              DocumentRepository documentRepository) {
        this.operationRepository = operationRepository;
        this.userRepository = userRepository;
        this.operationMapper = operationMapper;
        this.accountRepository = accountRepository;
        this.documentRepository = documentRepository;
    }

    @Override
    @Transactional
    public void createDeposit(DepositeDto depositeDto ,String email)  {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException());
        Account account = accountRepository.findByOwner(user).orElseThrow(()->new AccountNotFoundException());
        if(!user.isActive()){
            throw new AccountInactiveException();
        }

        Operation operation = operationMapper.dtoToEntity(depositeDto);

        if(operation.getAmount() <= 10000){
            operation.setStatus(OperationStatus.APPROVE);
            operation.setAccountDestination(account);
            operation.setCreatedAt(LocalDateTime.now());
            operation.setExecutedAt(LocalDateTime.now());
            operation.setValidatedAt(LocalDateTime.now());
            account.setBalance(account.getBalance() + operation.getAmount());
            accountRepository.save(account);
        }else{
           operation.setStatus(OperationStatus.PENDING);
           operation.setAccountDestination(account);
           operation.setCreatedAt(LocalDateTime.now());
        }
        operationRepository.save(operation);
    }
}
