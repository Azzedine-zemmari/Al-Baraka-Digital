package com.banc.securise.mapper;

import com.banc.securise.Dto.TransferDto;
import com.banc.securise.entity.Operation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransferMapper {
    @Mapping(source = "destinationAccountId",target="accountDestination")
    @Mapping(source = "amount", target = "amount")
    @Mapping(source = "type", target = "type")
    Operation toEntity(TransferDto dto);
    @Mapping(source = "accountDestination",target="destinationAccountId")
    @Mapping(source = "amount", target = "amount")
    @Mapping(source = "type", target = "type")
    TransferDto toDto(Operation entity);
}
