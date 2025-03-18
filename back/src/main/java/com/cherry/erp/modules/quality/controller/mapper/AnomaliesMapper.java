package com.cherry.erp.modules.quality.controller.mapper;

import com.cherry.erp.modules.projet.model.dto.GammeDto;
import com.cherry.erp.modules.projet.model.dto.ProjectsDto;
import com.cherry.erp.modules.projet.model.persistent.Gamme;
import com.cherry.erp.modules.projet.model.persistent.Projects;
import com.cherry.erp.modules.quality.model.dto.AnomaliesDto;
import com.cherry.erp.modules.quality.model.persistent.Anomalies;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class AnomaliesMapper {
    public abstract Anomalies toEntity(AnomaliesDto anomaliesDto);
    public abstract AnomaliesDto toDto(Anomalies anomalies);

    public abstract List<AnomaliesDto> toDtoList(List<Anomalies> anomaliesList);


    @AfterMapping
    public void mapExtraAttributes(Anomalies anomalies, @MappingTarget AnomaliesDto anomaliesDto) {}
}
