package com.cherry.erp.modules.projet.model.dto;

import com.cherry.erp.common.model.dto.GenericDto;
import com.cherry.erp.modules.projet.model.enums.StatutProjetEnum;
import com.cherry.erp.modules.projet.model.persistent.Tache;
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
public class ProjectsDto extends GenericDto {
    private String reference;
    private String titre;
    private String localisation;
    private StatutProjetEnum statut;
    private String designationProjet;
    private String numBC;
    private Date datePrevueLancement;
    private Date dateLancement;
    private Date datePrevueLivraison;
    private Date dateLivraison;
    private Date dateCloture;
    private Boolean production;
    private Set<Tache> taches;
    private Employee employee;

}
