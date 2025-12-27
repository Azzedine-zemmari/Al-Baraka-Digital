package com.banc.securise.service.transmeter;

import com.banc.securise.Dto.TransferDto;
import com.banc.securise.entity.Account;
import com.banc.securise.entity.Document;
import com.banc.securise.entity.Operation;
import com.banc.securise.entity.User;
import com.banc.securise.enums.OperationStatus;
import com.banc.securise.enums.OperationType;
import com.banc.securise.exception.AccountInactiveException;
import com.banc.securise.mapper.TransferMapper;
import com.banc.securise.repository.AccountRepository;
import com.banc.securise.repository.DocumentRepository;
import com.banc.securise.repository.OperationRepository;
import com.banc.securise.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TransferServiceImpl implements TransferService {
    private final OperationRepository operationRepository;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final TransferMapper transferMapper;
    private final DocumentRepository documentRepository;
    @Value("${app.upload.dir}")
    private String uploadDir;

    public TransferServiceImpl(OperationRepository operationRepository, AccountRepository accountRepository, UserRepository userRepository, TransferMapper transferMapper,DocumentRepository documentRepository) {
        this.operationRepository = operationRepository;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.transferMapper = transferMapper;
        this.documentRepository = documentRepository;
    }

    @Override
    @Transactional
    public void createTransfer(TransferDto dto, MultipartFile justificatif , String email) throws IOException {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("user not found"));
        Account accountSource = accountRepository.findByOwner(user).orElseThrow(()-> new RuntimeException("account not found"));
        if(!user.isActive()){
            throw new AccountInactiveException();
        }
        if (dto.getAmount() <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        Operation operation = transferMapper.toEntity(dto);
        User userDestination = userRepository.findById(dto.getDestinationAccountId()).orElseThrow(()-> new RuntimeException("user destination not found"));
        Account accountDestination  = accountRepository.findByOwner(userDestination).orElseThrow(()-> new RuntimeException("account destination not found"));
        if(dto.getAmount() <= 10000){
            if(accountSource.getBalance() >= dto.getAmount()){
                operation.setStatus(OperationStatus.APPROVE);
                operation.setAccountDestination(accountDestination);
                operation.setAccountSource(accountSource);
                operation.setCreatedAt(LocalDateTime.now());
                operation.setExecutedAt(LocalDateTime.now());
                operation.setValidatedAt(LocalDateTime.now());
                accountSource.setBalance(accountSource.getBalance() - dto.getAmount());
                accountDestination.setBalance(accountDestination.getBalance() + dto.getAmount());
                accountRepository.save(accountSource);
                accountRepository.save(accountDestination);
            }
        }
        else{
            operation.setAccountDestination(accountDestination);
            operation.setAccountSource(accountSource);
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
    @Override
    @Transactional
    public String confirmTransfer(Integer id){
        Operation op = operationRepository.findById(id).orElseThrow(() -> new RuntimeException("operation not found"));
        if (op.getStatus().equals(OperationStatus.PENDING) && op.getType().equals(OperationType.TRANSFER)) {
            op.setStatus(OperationStatus.APPROVE);
            op.setValidatedAt(LocalDateTime.now());
            op.setExecutedAt(LocalDateTime.now());
            operationRepository.save(op);
            Account accountDestination = op.getAccountDestination();
            Account accountSource = op.getAccountSource();
            accountDestination.setBalance(accountDestination.getBalance() + op.getAmount());
            accountSource.setBalance(accountSource.getBalance() - op.getAmount());
            accountRepository.save(accountDestination);
            accountRepository.save(accountSource);
            return "Operation confirmed";
        }
        return "Operation status or type are not correct";
    }
    @Override
    public  String cancelTransfer(Integer id){
        Operation op = operationRepository.findById(id).orElseThrow(() -> new RuntimeException("operation not found"));
        if (op.getStatus().equals(OperationStatus.PENDING) && op.getType().equals(OperationType.TRANSFER)) {
            op.setStatus(OperationStatus.CANCELED);
            op.setValidatedAt(LocalDateTime.now());
            op.setExecutedAt(LocalDateTime.now());
            operationRepository.save(op);
            return "Operation confirmed";
        }
        return "Operation status or type are not correct";
    }
}
