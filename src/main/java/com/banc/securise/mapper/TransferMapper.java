package com.banc.securise.mapper;

import com.banc.securise.Dto.TransferDto;
import com.banc.securise.entity.Operation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransferMapper {
    Operation toEntity(TransferDto dto);
    TransferDto toDto(Operation entity);
}
