package com.cherry.erp.modules.production.controller.Mapper;

import com.cherry.erp.modules.production.model.dto.ProductDto;
import com.cherry.erp.modules.production.model.dto.QuantityProductDto;
import com.cherry.erp.modules.production.model.persistent.Product;
import com.cherry.erp.modules.production.model.persistent.QuantityProduct;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class QuantityProductMapper {
    public abstract QuantityProduct toEntity(QuantityProductDto quantityProductDto);
    public abstract QuantityProductDto toDto(QuantityProduct product);

    public abstract List<QuantityProductDto> toDtoList(List<QuantityProduct> quantityProductList);


    @AfterMapping
    public void mapExtraAttributes(QuantityProduct quantityProduct, @MappingTarget QuantityProductDto quantityProductDto) {}
}
