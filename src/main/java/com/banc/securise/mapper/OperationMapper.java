package com.banc.securise.mapper;

import com.banc.securise.Dto.DepositeDto;
import com.banc.securise.entity.Operation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OperationMapper {
    DepositeDto entityToDto(Operation operation);
    Operation dtoToEntity(DepositeDto depositeDto);
}
