package com.cherry.erp.modules.quality.model.persistent;

import com.cherry.erp.modules.quality.model.enums.DecisionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "fnc_anomalie")
@SequenceGenerator(name = "SEQ", sequenceName = "fnc_anomalie_seq", allocationSize = 1)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FNCAnomalies  {
    private static final long serialVersionUID = 1307346987174803693L;

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "SEQ")
    protected Integer id = 0;
    private Integer nc ;
    private Double rebut ;
    private Double cout ;
    private String description;
    private String cause;
    @Enumerated(EnumType.STRING)
    private DecisionEnum decision;

    @ManyToOne
    private Anomalies anomalies;

    @ManyToOne
    private FNC FNC;
}
