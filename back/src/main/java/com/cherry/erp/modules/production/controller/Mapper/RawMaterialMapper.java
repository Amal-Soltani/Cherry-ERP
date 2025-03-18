package com.cherry.erp.modules.production.controller.Mapper;

import com.cherry.erp.modules.production.model.dto.RawMaterialDto;
import com.cherry.erp.modules.production.model.persistent.RawMaterial;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class RawMaterialMapper {
    public abstract RawMaterial toEntity(RawMaterialDto rawMaterialDto);

    public abstract RawMaterialDto toDto(RawMaterial rawMaterial);

    public abstract List<RawMaterialDto> toDtoList(List<RawMaterial> rawMaterialList);


    @AfterMapping
    public void mapExtraAttributes(RawMaterial rawMaterial, @MappingTarget RawMaterialDto rawMaterialDto) {}
}
