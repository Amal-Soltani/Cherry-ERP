package com.cherry.erp.modules.projet.model.dto;

import com.cherry.erp.common.model.dto.GenericDto;
import com.cherry.erp.modules.production.model.persistent.ProductionLigne;
import com.cherry.erp.modules.projet.model.persistent.Projects;
import com.cherry.erp.modules.projet.model.persistent.Tache;
import com.cherry.erp.modules.v2.hr.model.persistent.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlanningDto extends GenericDto {
    private Date dateDebut;
    private Date dateFin;
    private Integer tempsArret;
    private String phase;
    private String raisonArret;
    private Integer tempsMachine;
    private Integer qteTotale;
    private Integer qteRebut;
    private Integer qteNC;
    private String note;
    private String equipment;
    private Tache tache;
    private ProductionLigne productionLigne;
    private Projects projects;
    private Employee employee;
}
