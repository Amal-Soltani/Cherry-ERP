package com.cherry.erp.modules.projet.controller.Mapper;

import com.cherry.erp.modules.projet.model.dto.GammeDto;
import com.cherry.erp.modules.projet.model.persistent.Gamme;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class GammeMapper {
    public abstract Gamme toEntity(GammeDto gammeDto);
    public abstract GammeDto toDto(Gamme gamme);

    public abstract List<GammeDto> toDtoList(List<Gamme> gammeList);


    @AfterMapping
    public void mapExtraAttributes(Gamme gamme, @MappingTarget GammeDto gammeDto) {}
}
