package com.cherry.erp.modules.production.model.dto;

import com.cherry.erp.common.model.dto.GenericDto;
import com.cherry.erp.modules.v2.hr.model.persistent.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductionLigneDto extends GenericDto {
    private String reference;
    private String name;
    private Employee employee;
}
