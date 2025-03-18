package com.cherry.erp.modules.quality.controller.mapper;

import com.cherry.erp.modules.projet.model.dto.GammeDto;
import com.cherry.erp.modules.projet.model.persistent.Gamme;
import com.cherry.erp.modules.quality.model.dto.FNCAnomaliesDto;
import com.cherry.erp.modules.quality.model.dto.FNCDto;
import com.cherry.erp.modules.quality.model.persistent.FNC;
import com.cherry.erp.modules.quality.model.persistent.FNCAnomalies;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class FNCMApper {
    public abstract FNC toEntity(FNCDto fammeDto);
    public abstract FNCDto toDto(FNC fNC);

    public abstract List<FNCDto> toDtoList(List<FNC> FNCList);


    @AfterMapping
    public void mapExtraAttributes(FNC fNC, @MappingTarget FNCDto fammeDto) {}
}
