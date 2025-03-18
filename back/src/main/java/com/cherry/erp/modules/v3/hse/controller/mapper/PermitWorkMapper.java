package com.cherry.erp.modules.v3.hse.controller.mapper;

import com.cherry.erp.modules.v3.hse.model.dto.PermitWorkDto;
import com.cherry.erp.modules.v3.hse.model.persistent.PermitWork;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class PermitWorkMapper {

    public abstract PermitWork toEntity(PermitWorkDto permitWorkDto);

    public abstract PermitWorkDto toDto(PermitWork permitWork);

    @AfterMapping
    public void mapExtraAttributes(PermitWork entity, @MappingTarget PermitWorkDto dto) {

    }

}
