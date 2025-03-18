package com.cherry.erp.modules.production.controller.Mapper;

import com.cherry.erp.modules.production.model.dto.BOMDto;
import com.cherry.erp.modules.production.model.persistent.BOM;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class BOMMapper {
    public abstract BOM toEntity(BOMDto BOMDto);

    public abstract BOMDto toDto(BOM BOM);

    public abstract List<BOMDto> toDtoList(List<BOM> BOMList);


    @AfterMapping
    public void mapExtraAttributes(BOM BOM, @MappingTarget BOMDto BOMDto) {}
}
