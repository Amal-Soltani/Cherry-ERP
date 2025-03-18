package com.cherry.erp.modules.projet.model.dto;

import com.cherry.erp.common.model.dto.GenericDto;
import com.cherry.erp.modules.production.model.persistent.Product;
import com.cherry.erp.modules.projet.model.persistent.GmPhase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GammeDto extends GenericDto {
    private Set<GmPhase> gmPhases;
    private Product product;
}
