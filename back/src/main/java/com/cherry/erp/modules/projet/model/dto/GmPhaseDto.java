package com.cherry.erp.modules.projet.model.dto;

import com.cherry.erp.common.model.dto.GenericDto;
import com.cherry.erp.modules.production.model.persistent.Phase;
import com.cherry.erp.modules.projet.model.persistent.Gamme;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GmPhaseDto extends GenericDto {
    private Integer temps ;
    private String[] article ;
    private String[] produits ;
    private String equipment;
    private String quantity;
    private Phase phase;
    private Gamme gamme;
}
