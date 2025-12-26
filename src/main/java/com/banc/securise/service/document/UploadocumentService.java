package com.banc.securise.service.document;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadocumentService {
    void uploadFile(int operationId , MultipartFile file) throws IOException;
}
