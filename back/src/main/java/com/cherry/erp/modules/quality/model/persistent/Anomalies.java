package com.cherry.erp.modules.quality.model.persistent;

import com.cherry.erp.common.model.persistent.GenericEntity;
import com.cherry.erp.company.model.persistent.Company;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "anomalie")
@SequenceGenerator(name = "SEQ", sequenceName = "anomalie_seq", allocationSize = 1)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Anomalies extends GenericEntity {
    private static final long serialVersionUID = 1307346987174803693L;
    private String categorie;
    private String type;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<FNCAnomalies> FNCAnomalies;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "COMPANY_ID")
    private Company company;
}
