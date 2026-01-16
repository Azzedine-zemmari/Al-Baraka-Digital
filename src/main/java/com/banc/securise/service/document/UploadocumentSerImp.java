package com.banc.securise.service.document;

import com.banc.securise.entity.Document;
import com.banc.securise.entity.Operation;
import com.banc.securise.entity.User;
import com.banc.securise.enums.OperationStatus;
import com.banc.securise.repository.DocumentRepository;
import com.banc.securise.repository.OperationRepository;
import com.banc.securise.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.Optional;
import  com.banc.securise.repository.AccountRepository;
import com.banc.securise.entity.Account;
import com.banc.securise.exception.UserNotFoundException;
import com.banc.securise.exception.AccountNotFoundException;
import com.banc.securise.exception.AccountInactiveException;



import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.*;

@Service
public class UploadocumentSerImp implements UploadocumentService{
    private DocumentRepository documentRepository;
    private OperationRepository operationRepository;
    private UserRepository userRepository;
    private AccountRepository accountRepository;
    @Value("${app.upload.dir}")
    private String uploadDir;

    public UploadocumentSerImp(DocumentRepository documentRepository, OperationRepository operationRepository , UserRepository userRepository,AccountRepository accountRepository) {
        this.documentRepository = documentRepository;
        this.operationRepository = operationRepository;
        this.userRepository = userRepository;
        this.accountRepository = accountRepository; 
    }

    @Override
    @Transactional
    public void uploadFile(int operationId , MultipartFile justificatif) throws IOException {
        Operation operation = operationRepository.findById(operationId).orElseThrow(()-> new RuntimeException("operation not found"));
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
        operationRepository.save(operation);
    }
    @Override
    public List<Operation> getUnjustifiedOperations(String email){
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException());
        Account account = accountRepository.findByOwner(user).orElseThrow(()->new AccountNotFoundException());
        if(!user.isActive()){
            throw new AccountInactiveException();
        }
        return operationRepository.findOperationsNotjustified(account,10000.00);
    }
}
