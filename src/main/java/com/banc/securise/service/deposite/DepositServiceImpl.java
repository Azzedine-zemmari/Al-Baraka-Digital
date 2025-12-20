package com.banc.securise.service.deposite;

import com.banc.securise.Dto.DepositeDto;
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
@AllArgsConstructor
public class DepositServiceImpl implements DepositService{
    private final OperationRepository operationRepository;
    private final UserRepository userRepository;
    private final OperationMapper operationMapper;
    private final AccountRepository AccountRepository;
    private final AccountRepository accountRepository;

    @Override
    public void createDeposit(DepositeDto depositeDto,String email) throws IOException {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new IllegalStateException("user not authenticated"));
        Account account = accountRepository.findByOwner(user).orElseThrow(()->new IllegalStateException("user has no account"));
        if(user.getActive().equals("false")){
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
            MultipartFile file = depositeDto.getJustificatif();
            if(file == null || file.isEmpty()){
                throw new IllegalArgumentException("justificatif is required for deposit amount above 10,000");
            }
            String contentType = file.getContentType();
            if(!(contentType.equals("application/pdf") ||
                    (contentType.equals("image/jpeg")) ||
                    (contentType.equals("image/png")) )){
                throw new IllegalArgumentException("invalid file type");
            }
            if(file.getSize() > 5 * 1024 * 1024){
                throw new IllegalArgumentException("invalid file size");
            }
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path path = Paths.get("uploads/" + fileName);
            Files.createDirectories(path.getParent());
            file.transferTo(path);
            operation.setStatus(OperationStatus.PENDING);
            // add justification to document entity
        }

        operationRepository.save(operation);

    }
}
