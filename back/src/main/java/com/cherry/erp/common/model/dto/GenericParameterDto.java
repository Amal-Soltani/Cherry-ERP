package com.cherry.erp.common.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenericParameterDto extends GenericDto{

    private Boolean stockShowAlertInFeed = true;

    private Boolean sendMailForStockAlert = true;

}
