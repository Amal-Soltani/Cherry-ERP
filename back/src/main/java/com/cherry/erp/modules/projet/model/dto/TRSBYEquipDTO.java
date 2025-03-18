package com.cherry.erp.modules.projet.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TRSBYEquipDTO {
    private String equipment;
    private Double tauxQualite;
    private Double tauxPerformance;
    private Double tauxDisponibilite;
    private Double TRS;
}
