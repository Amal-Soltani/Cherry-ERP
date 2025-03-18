package com.cherry.erp.modules.projet.model.dto;

import com.cherry.erp.common.model.dto.GenericDto;
import com.cherry.erp.company.model.persistent.Company;
import com.cherry.erp.modules.production.model.persistent.Product;
import com.cherry.erp.modules.projet.model.enums.PriorityTacheEnum;
import com.cherry.erp.modules.projet.model.enums.StatutTacheEnum;
import com.cherry.erp.modules.projet.model.persistent.Projects;
import com.cherry.erp.modules.v2.hr.model.persistent.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TacheDto extends GenericDto {
    private String reference;
    private String titre ;
    private String designation ;
    private Integer quantite;
    private String ofexterne;
    private String bonCmd;
    private Integer parent ;
    private Date dateDebutPrev;
    private Date dateFinPrev;
    private Date dateDebutReel;
    private Date dateFinReel;
    private PriorityTacheEnum priority;
    private StatutTacheEnum status;
    private Product product;
    private Projects projet;
    private Employee employee;
}
