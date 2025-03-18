package com.cherry.erp.modules.production.model.persistent;

import com.cherry.erp.common.model.persistent.GenericEntity;
import com.cherry.erp.company.model.persistent.Company;
import com.cherry.erp.modules.production.model.enums.TypeProductEnum;
import com.cherry.erp.modules.production.model.enums.UnitMeasurementEnum;
import com.cherry.erp.modules.projet.model.persistent.Gamme;
import com.cherry.erp.modules.production.model.persistent.BOM;

import lombok.*;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "product")
@SequenceGenerator(name = "SEQ", sequenceName = "product_seq", allocationSize = 1)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product extends GenericEntity {
    private static final long serialVersionUID = 1307346987174803693L;

    @Column(name = "reference")
    private String reference;
    private String name;
    private String libelle;
    private String matiere;

    private String designation ;
    @Enumerated(EnumType.STRING)
    private TypeProductEnum typeProduct;

    @Enumerated(EnumType.STRING)
    private UnitMeasurementEnum unitMeasurement;

    @OneToOne(cascade = CascadeType.ALL)
    private BOM BOM;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<RawMaterial> rawMaterial;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<QuantityProduct> quantityProducts;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "COMPANY_ID")
    private Company company;

    @OneToOne(cascade = CascadeType.ALL)
    private Gamme gamme;

}
