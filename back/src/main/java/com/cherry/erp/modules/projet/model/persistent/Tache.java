package com.cherry.erp.modules.projet.model.persistent;

import com.cherry.erp.common.model.persistent.GenericEntity;
import com.cherry.erp.company.model.persistent.Company;
import com.cherry.erp.modules.production.model.persistent.Product;
import com.cherry.erp.modules.projet.model.enums.PriorityTacheEnum;
import com.cherry.erp.modules.projet.model.enums.StatutTacheEnum;
import com.cherry.erp.modules.v2.hr.model.persistent.Employee;
import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "tache")
@SequenceGenerator(name = "SEQ", sequenceName = "tache_seq", allocationSize = 1)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tache extends GenericEntity {
    private static final long serialVersionUID = 1307346987174803693L;

    @Column(name = "reference")
    private String reference;
    private String titre ;
    private String designation ;
    private Integer quantite;
    private String ofexterne;
    private String bonCmd;
    private Integer parent ;
    @Temporal(TemporalType.DATE)
    private Date dateDebutPrev;
    @Temporal(TemporalType.DATE)
    private Date dateFinPrev;
    @Temporal(TemporalType.DATE)
    private Date dateDebutReel;
    @Temporal(TemporalType.DATE)
    private Date dateFinReel;

    @Enumerated(EnumType.STRING)
    private PriorityTacheEnum priority;
    @Enumerated(EnumType.STRING)
    private StatutTacheEnum status;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Projects projet;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "COMPANY_ID")
    private Company company;

    @ManyToOne(fetch = FetchType.EAGER)
    private Employee employee;


}
