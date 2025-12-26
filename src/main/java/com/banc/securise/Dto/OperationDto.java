package com.banc.securise.Dto;

import com.banc.securise.entity.Account;
import com.banc.securise.enums.OperationStatus;
import com.banc.securise.enums.OperationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OperationDto {
    private Integer id;
    private OperationType type;
    private Double amount;
    private LocalDateTime createdAt;
    private OperationStatus status;
    private Account accountSource;
    private Account accountDestination;
}
