package com.cherry.erp.modules.stock.model.persistent;


import com.cherry.erp.common.model.persistent.GenericEntity;
import com.cherry.erp.company.model.persistent.Company;
import com.cherry.erp.modules.stock.model.enums.UniteMesureEnum;
import com.cherry.erp.modules.production.model.persistent.RawMaterial;
import lombok.*;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "article")
@SequenceGenerator(name = "SEQ", sequenceName = "article_seq", allocationSize = 1)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Article extends GenericEntity {
    private static final long serialVersionUID = 1307346987174803693L;
    @Column(name = "reference")
    private String reference;
    private String name;
    private String libelle;
    private String matiere;
    private String couleur;
    private double taille;
    private double longueur;
    private double largeur;
    private double hauteur;
    private String designation;

    @Enumerated(EnumType.STRING)
    private UniteMesureEnum uniteMesure;


    @OneToMany(cascade = CascadeType.ALL)
    private Set <RawMaterial> rawMaterials;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "COMPANY_ID")
    private Company company;
}
