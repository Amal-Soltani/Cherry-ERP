package com.cherry.erp.modules.production.model.dto;

import com.cherry.erp.common.model.dto.GenericDto;
import com.cherry.erp.modules.projet.model.persistent.GmPhase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhaseDto extends GenericDto {
    private String name;
    private String description;
    private Set<GmPhase> gmPhases;

}
