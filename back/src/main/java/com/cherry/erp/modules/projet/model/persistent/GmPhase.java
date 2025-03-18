package com.cherry.erp.modules.projet.model.persistent;

import com.cherry.erp.common.model.persistent.GenericEntity;
import com.cherry.erp.modules.production.model.persistent.Phase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "gm_phase")
@SequenceGenerator(name = "SEQ", sequenceName = "gm_phase_seq", allocationSize = 1)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GmPhase {
    private static final long serialVersionUID = 1307346987174803693L;
    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "SEQ")
    protected Integer id = 0;
    private Integer temps ;
    private String[] article ;
    private String[] produits ;
    private String equipment;
    private String quantity;

    @ManyToOne
    private Phase phase;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private Gamme gamme;
}
