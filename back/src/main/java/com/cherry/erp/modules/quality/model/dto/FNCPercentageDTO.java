package com.cherry.erp.modules.quality.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FNCPercentageDTO {
    private String categorie;
    private Long fncCount;
    private Long totalFNC;
    private Double percentage;
}
