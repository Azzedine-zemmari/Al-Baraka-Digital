package com.banc.securise.service.transmeter;

import com.banc.securise.Dto.TransferDto;

public interface TransferService {
    void createTransfer(TransferDto dto, String email) ;

}
