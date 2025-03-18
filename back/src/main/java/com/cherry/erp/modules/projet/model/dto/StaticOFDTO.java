package com.cherry.erp.modules.projet.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class StaticOFDTO {
    String phase;
    Long quantite;
    Long qteTotaleSum;
    Long qtencSum;
    Double qteRebutSum;
    Double tempsReel;
    Double tempsEstime;
}
