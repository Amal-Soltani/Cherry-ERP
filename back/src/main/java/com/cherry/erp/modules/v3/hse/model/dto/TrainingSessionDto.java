package com.cherry.erp.modules.v3.hse.model.dto;

import com.cherry.erp.common.model.dto.GenericDto;
import com.cherry.erp.company.model.persistent.Company;
import com.cherry.erp.modules.v2.hr.model.persistent.Employee;
import com.cherry.erp.modules.v3.hse.model.persistent.Training;
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
public class TrainingSessionDto extends GenericDto {


    private String reference;

    private Training subject;

    private Date startDate;

    private Integer[] participants = new Integer[0];

    private String image;

}
