package com.cherry.erp.modules.v3.hse.controller.mapper;

import com.cherry.erp.modules.v3.hse.model.dto.CorrectiveActionDto;
import com.cherry.erp.modules.v3.hse.model.dto.RiskAssessmentDto;
import com.cherry.erp.modules.v3.hse.model.persistent.CorrectiveAction;
import com.cherry.erp.modules.v3.hse.model.persistent.RiskAssessment;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class CorrectiveActionMapper {

    public abstract CorrectiveAction toEntity(CorrectiveActionDto correctiveActionDto);

    public abstract CorrectiveActionDto toDto(CorrectiveAction correctiveAction);

    @AfterMapping
    public void mapExtraAttributes(CorrectiveAction entity, @MappingTarget CorrectiveActionDto dto) {

    }

}
