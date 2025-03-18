package com.cherry.erp.modules.quality.controller.mapper;

import com.cherry.erp.modules.projet.model.dto.GammeDto;
import com.cherry.erp.modules.projet.model.persistent.Gamme;
import com.cherry.erp.modules.quality.model.dto.AnomaliesDto;
import com.cherry.erp.modules.quality.model.dto.FNCAnomaliesDto;
import com.cherry.erp.modules.quality.model.persistent.Anomalies;
import com.cherry.erp.modules.quality.model.persistent.FNCAnomalies;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class FNCAnomaliesMapper {
    public abstract FNCAnomalies toEntity(FNCAnomaliesDto fNCAnomaliesDto);
    public abstract FNCAnomaliesDto toDto(FNCAnomalies fNCAnomalies);

    public abstract List<FNCAnomaliesDto> toDtoList(List<FNCAnomalies> fNCAnomaliesList);


    @AfterMapping
    public void mapExtraAttributes(FNCAnomalies fNCAnomalies, @MappingTarget FNCAnomaliesDto fNCAnomaliesDto) {}
}
