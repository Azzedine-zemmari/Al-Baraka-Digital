package com.banc.securise.service.deposite;

import com.banc.securise.Dto.DepositeDto;
import com.banc.securise.entity.Account;
import com.banc.securise.entity.Document;
import com.banc.securise.entity.Operation;
import com.banc.securise.entity.User;
import com.banc.securise.enums.OperationStatus;
import com.banc.securise.exception.AccountInactiveException;
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
    public void createDeposit(DepositeDto depositeDto, MultipartFile justificatif ,String email) throws IOException {
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
            operation.setAccountDestination(account);
            operation.setCreatedAt(LocalDateTime.now());
            if(justificatif == null || justificatif.isEmpty()){
                throw new IllegalArgumentException("justificatif is required for deposit amount above 10,000");
            }

            String contentType = justificatif.getContentType();
            if(!(contentType.equals("application/pdf") ||
                    (contentType.equals("image/jpeg")) ||
                    (contentType.equals("image/png")) )){
                throw new IllegalArgumentException("invalid file type");
            }

            if(justificatif.getSize() > 5 * 1024 * 1024){
                throw new IllegalArgumentException("invalid file size");
            }

            String fileName = UUID.randomUUID() + "_" + justificatif.getOriginalFilename();

            Path uploadPath = Paths.get(uploadDir);
            Files.createDirectories(uploadPath);

            Path filePath = uploadPath.resolve(fileName);
            justificatif.transferTo(filePath.toFile());

            operation.setStatus(OperationStatus.PENDING);

            Document document = new Document();
            document.setFileName(fileName);
            document.setOperation(operation);
            document.setUploadedAt(LocalDateTime.now());
            document.setFileType(contentType);
            document.setStoragePath(filePath.toString());

            documentRepository.save(document);

        }

        operationRepository.save(operation);

    }
}
