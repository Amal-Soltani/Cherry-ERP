package com.cherry.erp.modules.projet.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class QteProduiteGammeEmpDTO {
    String phase;
    Long employeeId;
    String employeeNumber;
    String firstName;
    String lastName;
    Long qteTotaleSum;
}
