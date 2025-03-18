package com.cherry.erp.modules.production.controller.Mapper;

import com.cherry.erp.modules.production.model.dto.PhaseDto;
import com.cherry.erp.modules.production.model.persistent.Phase;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class PhaseMapper {
    public abstract Phase toEntity(PhaseDto phaseDto);
    public abstract PhaseDto toDto(Phase Phase);

    public abstract List<PhaseDto> toDtoList(List<Phase> phaseList);


    @AfterMapping
    public void mapExtraAttributes(Phase Phase, @MappingTarget PhaseDto phaseDto) {}
}
