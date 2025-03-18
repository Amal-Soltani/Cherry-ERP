package com.cherry.erp.modules.v3.hse.model.dto;


import com.cherry.erp.common.model.dto.GenericDto;
import com.cherry.erp.company.model.persistent.Company;
import com.cherry.erp.modules.v3.hse.model.persistent.Incident;
import com.cherry.erp.modules.v3.hse.model.persistent.PlanAction;
import com.cherry.erp.modules.v3.hse.model.persistent.RiskAssessment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CorrectiveActionDto extends GenericDto {

    private String reference;
    private PlanAction planAction;
    private Incident incident;

    private RiskAssessment riskAssessment;

    private String department;//responsible for execution of action

    private String description;

    private Date startDate;

    private Date endDate;

    private Integer progress;

    private String image;

}
