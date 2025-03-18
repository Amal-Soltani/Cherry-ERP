package com.cherry.erp.modules.production.controller.Mapper;

import com.cherry.erp.modules.production.model.dto.PhaseDto;
import com.cherry.erp.modules.production.model.dto.ProductDto;
import com.cherry.erp.modules.production.model.persistent.Phase;
import com.cherry.erp.modules.production.model.persistent.Product;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract  class ProductMapper {
    public abstract Product toEntity(ProductDto productDto);
    public abstract ProductDto toDto(Product product);

    public abstract List<ProductDto> toDtoList(List<Product> productList);


    @AfterMapping
    public void mapExtraAttributes(Product product, @MappingTarget ProductDto productDto) {}
}
