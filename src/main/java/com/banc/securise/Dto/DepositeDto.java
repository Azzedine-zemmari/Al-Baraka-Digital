package com.banc.securise.Dto;

import com.banc.securise.entity.Account;
import com.banc.securise.enums.OperationStatus;
import com.banc.securise.enums.OperationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.servlet.autoconfigure.MultipartAutoConfiguration;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
public class DepositeDto {
    private Integer id;
    private OperationType type;
    private Double amount;
    private MultipartFile justificatif;
}
