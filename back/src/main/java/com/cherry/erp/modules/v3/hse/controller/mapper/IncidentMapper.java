package com.cherry.erp.modules.v3.hse.controller.mapper;

import com.cherry.erp.modules.v3.hse.model.dto.IncidentDto;
import com.cherry.erp.modules.v3.hse.model.persistent.Incident;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class IncidentMapper {

    public abstract Incident toEntity(IncidentDto employeeDto);

    public abstract IncidentDto toDto(Incident employee);

    @AfterMapping
    public void mapExtraAttributes(Incident entity, @MappingTarget IncidentDto dto) {

    }

}
