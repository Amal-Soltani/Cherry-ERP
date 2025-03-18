package com.cherry.erp.modules.production.model.persistent;

import com.cherry.erp.common.model.persistent.GenericEntity;
import com.cherry.erp.company.model.persistent.Company;
import com.cherry.erp.modules.v2.hr.model.persistent.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "production_ligne")
@SequenceGenerator(name = "SEQ", sequenceName = "production_ligne_seq", allocationSize = 1)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ProductionLigne extends GenericEntity {
    private static final long serialVersionUID = 1307346987174803693L;

    private String reference;
    private String name;

    @ManyToOne
    private Employee employee;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "COMPANY_ID")
    private Company company;
}
