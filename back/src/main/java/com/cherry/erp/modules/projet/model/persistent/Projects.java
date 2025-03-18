package com.cherry.erp.modules.projet.model.persistent;

import com.cherry.erp.common.model.persistent.GenericEntity;
import com.cherry.erp.company.model.persistent.Company;
import com.cherry.erp.modules.projet.model.enums.StatutProjetEnum;
import com.cherry.erp.modules.v2.hr.model.persistent.Employee;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "projects")
@SequenceGenerator(name = "SEQ", sequenceName = "projects_seq", allocationSize = 1)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Projects extends GenericEntity {
    private static final long serialVersionUID = 1307346987174803693L;

    private String reference;
     private String titre;
     private String localisation;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(255) DEFAULT 'brouillon'")
    private StatutProjetEnum statut;
     private String designationProjet;
     private String numBC;
    @Temporal(TemporalType.DATE)
    private Date datePrevueLancement;
    @Temporal(TemporalType.DATE)
    private Date dateLancement;
    @Temporal(TemporalType.DATE)
    private Date datePrevueLivraison;
    @Temporal(TemporalType.DATE)
    private Date dateLivraison;
    @Temporal(TemporalType.DATE)
    private Date dateCloture;
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean production;

    @ManyToOne(fetch = FetchType.EAGER)
    private Employee employee;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "COMPANY_ID")
    private Company company;

}
