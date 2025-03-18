package com.cherry.erp.modules.production.model.persistent;

import com.cherry.erp.common.model.persistent.GenericEntity;
import com.cherry.erp.company.model.persistent.Company;
import com.cherry.erp.modules.projet.model.persistent.GmPhase;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "phase")
@SequenceGenerator(name = "SEQ", sequenceName = "phase_seq", allocationSize = 1)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Phase extends GenericEntity {
    private static final long serialVersionUID = 1307346987174803693L;
    private String name;
    private String description;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<GmPhase> gmPhases;

    @ManyToOne
    @JoinColumn(name = "COMPANY_ID")
    private Company company;
}
