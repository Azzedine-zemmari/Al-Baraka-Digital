package com.banc.securise.Dto;

import com.banc.securise.enums.OperationType;
import lombok.Data;

@Data
public class RetraitDto {
    private Integer id;
    private OperationType type;
    private Double amount;
}
