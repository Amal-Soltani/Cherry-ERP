package com.cherry.erp.modules.quality.model.dto;

import com.cherry.erp.common.model.dto.GenericDto;
import com.cherry.erp.modules.quality.model.enums.DecisionEnum;
import com.cherry.erp.modules.quality.model.persistent.Anomalies;
import com.cherry.erp.modules.quality.model.persistent.FNC;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FNCAnomaliesDto extends GenericDto {
    private Integer nc ;
    private Integer rebut ;
    private Double cout ;
    private String description;
    private String cause;
    private DecisionEnum decision;
    private Anomalies anomalies;
    private FNC FNC;
}
