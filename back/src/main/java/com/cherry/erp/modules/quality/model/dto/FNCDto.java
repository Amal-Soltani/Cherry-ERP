package com.cherry.erp.modules.quality.model.dto;

import com.cherry.erp.common.model.dto.GenericDto;
import com.cherry.erp.modules.projet.model.persistent.Tache;
import com.cherry.erp.modules.quality.model.persistent.FNCAnomalies;
import com.cherry.erp.modules.v2.hr.model.persistent.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FNCDto extends GenericDto {
    private String reference;
    private Integer projet;
    private String equipment;
    private Date date;
    private Set<FNCAnomalies> FNCAnomalies;
    private Employee employee;
    private Tache tache;
}
