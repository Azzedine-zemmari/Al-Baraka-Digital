package com.banc.securise.mapper;

import com.banc.securise.Dto.DepositeDto;
import com.banc.securise.entity.Operation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OperationMapper {
    @Mapping(source = "amount", target = "amount")
    @Mapping(source = "type", target = "type")
    DepositeDto entityToDto(Operation operation);
    @Mapping(source = "amount", target = "amount")
    @Mapping(source = "type", target = "type")
    Operation dtoToEntity(DepositeDto depositeDto);
}
