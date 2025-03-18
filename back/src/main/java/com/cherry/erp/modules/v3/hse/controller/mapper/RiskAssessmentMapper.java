package com.cherry.erp.modules.v3.hse.controller.mapper;

import com.cherry.erp.modules.v3.hse.model.dto.RiskAssessmentDto;
import com.cherry.erp.modules.v3.hse.model.persistent.RiskAssessment;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class RiskAssessmentMapper {

    public abstract RiskAssessment toEntity(RiskAssessmentDto employeeDto);

    public abstract RiskAssessmentDto toDto(RiskAssessment employee);

    @AfterMapping
    public void mapExtraAttributes(RiskAssessment entity, @MappingTarget RiskAssessmentDto dto) {

    }

}
