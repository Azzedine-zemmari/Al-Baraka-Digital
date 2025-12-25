package com.banc.securise.service.retrait;

import com.banc.securise.Dto.DepositeDto;
import com.banc.securise.Dto.RetraitDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface RetraitService {
    void createRetrait(DepositeDto dto, MultipartFile justificatif,String email) throws IOException;
    String confirmRetrait(Integer id);
//    String rejectRetrait(Integer id);
}
