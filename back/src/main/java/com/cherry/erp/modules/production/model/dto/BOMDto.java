package com.cherry.erp.modules.production.model.dto;

import com.cherry.erp.common.model.dto.GenericDto;
import com.cherry.erp.modules.production.model.enums.ManufacturingTypeEnum;
import com.cherry.erp.modules.production.model.persistent.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BOMDto extends GenericDto {
    private String reference;
    private String indice;
    private String designation;
    private ManufacturingTypeEnum manufacturingType;
    private String[] manufacturingProcesses ;
    private Product product;
}
