package com.cherry.erp.modules.v3.hse.model.dto;

import com.cherry.erp.common.model.dto.GenericDto;
import com.cherry.erp.company.model.persistent.Company;
import com.cherry.erp.modules.v2.hr.model.persistent.Employee;
import com.cherry.erp.modules.v3.hse.model.enums.PermitWorkTypeEnum;
import com.cherry.erp.modules.v3.hse.model.persistent.CorrectiveAction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PermitWorkDto extends GenericDto {

    private String reference;

    private PermitWorkTypeEnum type;

    private CorrectiveAction correctiveAction;

    private Integer initiator;

    private Integer[] executors = new Integer[0];

    private String description;

    private String risks;

    private Date startDate;

    private String image;
}
