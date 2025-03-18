package com.cherry.erp.modules.documentaire.model.dto;

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
public class DocumentDto extends GenericDto {
    private String reference;
    private String name;
    private String indice;
    private double size;
    private String type;
    private byte[] data;
    private Product product;
}
