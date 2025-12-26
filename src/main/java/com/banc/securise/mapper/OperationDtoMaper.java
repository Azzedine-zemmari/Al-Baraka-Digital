package com.banc.securise.mapper;

import com.banc.securise.Dto.OperationDto;
import com.banc.securise.entity.Operation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OperationDtoMaper {
    Operation dtoToEntity(OperationDto dto);
    OperationDto entityToDto(Operation op);
}
