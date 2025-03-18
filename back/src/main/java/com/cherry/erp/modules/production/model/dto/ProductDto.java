package com.cherry.erp.modules.production.model.dto;

import com.cherry.erp.common.model.dto.GenericDto;
import com.cherry.erp.modules.documentaire.model.persistent.Document;
import com.cherry.erp.modules.production.model.enums.TypeProductEnum;
import com.cherry.erp.modules.production.model.enums.UnitMeasurementEnum;
import com.cherry.erp.modules.production.model.persistent.BOM;
import com.cherry.erp.modules.production.model.persistent.RawMaterial;
import com.cherry.erp.modules.production.model.persistent.QuantityProduct;
import com.cherry.erp.modules.projet.model.persistent.Gamme;
import com.cherry.erp.modules.projet.model.persistent.Tache;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto extends GenericDto {
    private String reference;
    private String name;
    private String libelle;
    private String matiere;
    private String designation ;
    private TypeProductEnum typeProduct;
    private UnitMeasurementEnum unitMeasurement;
    private BOM BOM;
    private Set<Tache> taches;
    private Set<RawMaterial> rawMaterial;
    private Set<QuantityProduct> quantityProducts;
    private Set<Document> documents;
    private Gamme gamme;
}
