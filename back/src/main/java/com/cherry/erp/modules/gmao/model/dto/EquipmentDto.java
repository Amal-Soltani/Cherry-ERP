package com.cherry.erp.modules.gmao.model.dto;

import com.cherry.erp.common.model.dto.GenericDto;
import com.cherry.erp.modules.gmao.model.enums.CategoryEnum;
import com.cherry.erp.modules.gmao.model.enums.StateEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentDto extends GenericDto {
    private String reference;
    private String name;
    private String emplacement;
    private Date purchaseDate;
    private Date releaseDate;
    private double purchasePrice;
    private String description;
    private StateEnum stateEnum;
    private CategoryEnum categoryEnum;

}
