package com.cherry.erp.company.model.dto;

import com.cherry.erp.common.model.dto.GenericDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReferenceConfigDto extends GenericDto {

    private String object; // class name

    private Integer year;

    private Integer number = 0;

    private String prefix;

    private String numberFormat;

}
