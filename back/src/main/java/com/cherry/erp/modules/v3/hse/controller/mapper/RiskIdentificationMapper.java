package com.cherry.erp.modules.v3.hse.controller.mapper;

import com.cherry.erp.modules.v3.hse.model.dto.RiskAssessmentDto;
import com.cherry.erp.modules.v3.hse.model.dto.RiskIdentificationDto;
import com.cherry.erp.modules.v3.hse.model.persistent.RiskAssessment;
import com.cherry.erp.modules.v3.hse.model.persistent.RiskIdentification;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class RiskIdentificationMapper {

    public abstract RiskIdentification toEntity(RiskIdentificationDto riskIdentificationDto);

    public abstract RiskIdentificationDto toDto(RiskIdentification riskIdentification);

    @AfterMapping
    public void mapExtraAttributes(RiskIdentification entity, @MappingTarget RiskIdentificationDto dto) {

    }

}
