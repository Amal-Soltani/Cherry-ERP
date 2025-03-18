package com.cherry.erp.modules.quality.model.dto;

import com.cherry.erp.common.model.dto.GenericDto;
import com.cherry.erp.modules.quality.model.persistent.FNCAnomalies;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnomaliesDto extends GenericDto {
    private String categorie;
    private String type;
    private Set<FNCAnomalies> FNCAnomalies;
}
