package com.banc.securise.Dto;

import com.banc.securise.entity.Account;
import com.banc.securise.enums.OperationType;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class TransferDto {
    private Integer id;
    private OperationType type;
    private Double amount;
    private Integer destinationAccountId;
}
