package com.cherry.erp.modules.v3.hse.controller.mapper;

import com.cherry.erp.modules.v3.hse.model.dto.CorrectiveActionDto;
import com.cherry.erp.modules.v3.hse.model.dto.PlanActionDto;
import com.cherry.erp.modules.v3.hse.model.persistent.CorrectiveAction;
import com.cherry.erp.modules.v3.hse.model.persistent.PlanAction;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
@Mapper(componentModel = "spring")
public abstract class PlanActionMapper {
    public abstract PlanAction toEntity(PlanActionDto planActionDto);

    public abstract PlanActionDto toDto(PlanAction planAction);

    @AfterMapping
    public void mapExtraAttributes(PlanAction entity, @MappingTarget PlanActionDto dto) {

    }
}
