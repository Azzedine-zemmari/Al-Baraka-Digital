package com.banc.securise.service.document;

import com.banc.securise.entity.Document;
import com.banc.securise.entity.Operation;
import com.banc.securise.enums.OperationStatus;
import com.banc.securise.repository.DocumentRepository;
import com.banc.securise.repository.OperationRepository;
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
public class UploadocumentSerImp implements UploadocumentService{
    private DocumentRepository documentRepository;
    private OperationRepository operationRepository;
    @Value("${app.upload.dir}")
    private String uploadDir;

    public UploadocumentSerImp(DocumentRepository documentRepository, OperationRepository operationRepository) {
        this.documentRepository = documentRepository;
        this.operationRepository = operationRepository;
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
}
