package com.cherry.erp.modules.production.controller.Mapper;

import com.cherry.erp.modules.production.model.dto.ProductionLigneDto;
import com.cherry.erp.modules.production.model.persistent.ProductionLigne;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ProductionLigneMapper {
    public abstract ProductionLigne toEntity(ProductionLigneDto productionLigneDto);

    public abstract ProductionLigneDto toDto(ProductionLigne productionLigne);

    public abstract List<ProductionLigneDto> toDtoList(List<ProductionLigne> productionLigneList);


    @AfterMapping
    public void mapExtraAttributes(ProductionLigne productionLigne, @MappingTarget ProductionLigneDto productionLigneDto) {}
}
