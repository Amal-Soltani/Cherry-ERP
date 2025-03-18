package com.cherry.erp.modules.projet.controller.Mapper;

import com.cherry.erp.modules.projet.model.dto.GmPhaseDto;
import com.cherry.erp.modules.projet.model.persistent.GmPhase;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class GmPhaseMapper {
    public abstract GmPhase toEntity(GmPhaseDto gmPhaseDto);
    public abstract GmPhaseDto toDto(GmPhase gmPhase);

    public abstract List<GmPhaseDto> toDtoList(List<GmPhase> gmPhaseList);

    @AfterMapping
    public void mapExtraAttributes(GmPhase gmPhase, @MappingTarget GmPhaseDto gmPhaseDto) {}
}
