package com.cherry.erp.modules.projet.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TRSBYLigneDTO {
    private Long ligneProductionId;
    private String name;
    private String reference;
    private Double tauxQualite;
    private Double tauxPerformance;
    private Double tauxDisponibilite;
    private Double TRS;
}
