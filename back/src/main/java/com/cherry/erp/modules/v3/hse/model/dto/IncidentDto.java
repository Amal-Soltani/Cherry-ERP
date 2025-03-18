package com.cherry.erp.modules.v3.hse.model.dto;

import com.cherry.erp.common.model.dto.GenericDto;
import com.cherry.erp.modules.v2.hr.model.persistent.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IncidentDto extends GenericDto {

    private String reference;

    private Date date;

    private Integer department;

    private String description;

    private Integer reporterId;



    private Integer totalInjury;

    private Integer[] injuries = new Integer[0];



    private Integer damagedEquipmentId;

    private double estimatedLoss;

    private String image;

}
