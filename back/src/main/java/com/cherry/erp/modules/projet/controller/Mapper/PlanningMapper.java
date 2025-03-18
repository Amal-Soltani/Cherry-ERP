package com.cherry.erp.modules.projet.controller.Mapper;

import com.cherry.erp.modules.projet.model.dto.PlanningDto;
import com.cherry.erp.modules.projet.model.persistent.Planning;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class PlanningMapper {
    public abstract Planning toEntity(PlanningDto planningDto);
    public abstract PlanningDto toDto(Planning planning);

    public abstract List<PlanningDto> toDtoList(List<Planning> planningList);

    @AfterMapping
    public void mapExtraAttributes(Planning planning, @MappingTarget PlanningDto planningDto) {}
}
