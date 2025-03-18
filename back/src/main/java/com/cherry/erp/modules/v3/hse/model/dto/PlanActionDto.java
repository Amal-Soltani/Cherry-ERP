package com.cherry.erp.modules.v3.hse.model.dto;

import com.cherry.erp.common.model.dto.GenericDto;
import com.cherry.erp.company.model.persistent.Company;
import com.cherry.erp.modules.v3.hse.model.enums.PlanActionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlanActionDto extends GenericDto {


    private String reference;


    private PlanActionEnum type;

     // id de l'incident ou riskAssessment ou FNC ....s
    private Integer triggerId;

    // le référence de l'incident ou riskAssessment ou FNC ....s
    private String triggerRef;

    //private Company company;
}
