package com.banc.securise.service.deposite;


import com.banc.securise.Dto.DepositeDto;

public interface DepositService {
    void createDeposit(DepositeDto depositeDto,String email);
}
