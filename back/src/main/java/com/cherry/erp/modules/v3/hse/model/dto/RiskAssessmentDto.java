package com.cherry.erp.modules.v3.hse.model.dto;

import com.cherry.erp.common.model.dto.GenericDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RiskAssessmentDto extends GenericDto {

    private String reference;

    private Integer responsableId;

    private String operation;

    private String risks;
    private String impacted;

    private String image;

}
