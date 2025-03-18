package com.cherry.erp.modules.projet.model.persistent;

import com.cherry.erp.common.model.persistent.GenericEntity;
import com.cherry.erp.modules.production.model.persistent.ProductionLigne;
import com.cherry.erp.modules.v2.hr.model.persistent.Employee;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "planning")
@SequenceGenerator(name = "SEQ", sequenceName = "planning_seq", allocationSize = 1)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Planning extends GenericEntity {
    private static final long serialVersionUID = 1307346987174803693L;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDebut;
    @Temporal(TemporalType.TIMESTAMP)
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

    @ManyToOne
    Tache tache;
    @OneToOne
    ProductionLigne productionLigne;
    @ManyToOne
    Projects projects;
    @ManyToOne
    Employee employee;

}
