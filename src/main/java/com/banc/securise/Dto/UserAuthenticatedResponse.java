package com.banc.securise.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.banc.securise.entity.User;
import java.util.List;
import com.banc.securise.entity.Operation;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthenticatedResponse{
    private String accountNumber;
    private Double balance;
    private User owner;
    private List<Operation> operations;
}