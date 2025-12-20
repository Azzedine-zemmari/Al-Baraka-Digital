package com.banc.securise.service.deposite;


import com.banc.securise.Dto.DepositeDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface DepositService {
    void createDeposit(DepositeDto depositeDto, MultipartFile justificatif , String email) throws IOException;
}
