package com.cherry.erp.modules.quality.model.persistent;

import com.cherry.erp.common.model.persistent.GenericEntity;
import com.cherry.erp.company.model.persistent.Company;
import com.cherry.erp.modules.projet.model.persistent.Tache;
import com.cherry.erp.modules.v2.hr.model.persistent.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;
@Entity
@Table(name = "fnc")
@SequenceGenerator(name = "SEQ", sequenceName = "fnc_seq", allocationSize = 1)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FNC extends GenericEntity {
    private static final long serialVersionUID = 1307346987174803693L;

    private String reference;
    private Integer projet;
    private String equipment;
    @Temporal(TemporalType.DATE)
    private Date date;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<FNCAnomalies> FNCAnomalies;

    @ManyToOne
    private Employee employee;
    @ManyToOne
    private Tache tache;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "COMPANY_ID")
    private Company company;
}
