package com.cherry.erp.modules.stock.model.dto;

import com.cherry.erp.common.model.dto.GenericDto;
import com.cherry.erp.modules.production.model.persistent.RawMaterial;
import com.cherry.erp.modules.stock.model.enums.UniteMesureEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto extends GenericDto {
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
    private UniteMesureEnum uniteMesure;
    private Set<RawMaterial> rawMaterials;
}
