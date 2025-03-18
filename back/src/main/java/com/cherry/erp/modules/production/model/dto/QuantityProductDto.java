package com.cherry.erp.modules.production.model.dto;

import com.cherry.erp.common.model.dto.GenericDto;
import com.cherry.erp.modules.production.model.persistent.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.ManyToOne;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuantityProductDto extends GenericDto {
    private Integer qteParAssemblage;
    private Product parent;
    private Product child;
}
