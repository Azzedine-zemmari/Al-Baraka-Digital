package com.banc.securise.service.transmeter;

import com.banc.securise.Dto.TransferDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface TransferService {
    void createTransfer(TransferDto dto, MultipartFile justificatif , String email) throws IOException;
    String confirmTransfer(Integer id);
    String cancelTransfer(Integer id);

}
