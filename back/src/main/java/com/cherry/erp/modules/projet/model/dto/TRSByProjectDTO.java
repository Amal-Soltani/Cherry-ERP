package com.cherry.erp.modules.projet.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TRSByProjectDTO {

    private Long qteTotaleSum;
    private Long qteNCSum;
    private Long qteRebutSum;
    private Long tempsReelSum;
    private Long tempsArretSum;
    private Long theoreticalQuantity;
    private Double tauxQualite;
    private Double tauxPerformance;
    private Double tauxDisponibilite;
    private Double TRS;


}
