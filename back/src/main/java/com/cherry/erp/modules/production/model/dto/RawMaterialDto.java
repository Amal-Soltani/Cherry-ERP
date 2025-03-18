package com.cherry.erp.modules.production.model.dto;

import com.cherry.erp.common.model.dto.GenericDto;
import com.cherry.erp.modules.production.model.persistent.Product;
import com.cherry.erp.modules.stock.model.persistent.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RawMaterialDto extends GenericDto {
    private String rawDimension;
    private double grossQuantity;
    private Article article;
    private Product product;
}
