package com.banc.securise.Dto;

import com.banc.securise.entity.Account;
import com.banc.securise.enums.OperationStatus;
import com.banc.securise.enums.OperationType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class DepositeDto {
    private Integer id;
    private OperationType type;
    private Double amount;
}
