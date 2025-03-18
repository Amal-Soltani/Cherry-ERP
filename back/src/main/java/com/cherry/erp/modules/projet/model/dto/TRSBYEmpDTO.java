package com.cherry.erp.modules.projet.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TRSBYEmpDTO {
    private Integer employeeID;
    private String employeeNumber;
    private String firstName;
    private String lastName;
    private Double tauxQualite;
    private Double tauxPerformance;
    private Double tauxDisponibilite;
    private Double TRS;
}
