package com.banc.securise.service.document;

import org.springframework.web.multipart.MultipartFile;
import com.banc.securise.entity.Operation;
import java.util.*;


import java.io.IOException;

public interface UploadocumentService {
    void uploadFile(int operationId , MultipartFile file) throws IOException;
    List<Operation> getUnjustifiedOperations(String email);
}
