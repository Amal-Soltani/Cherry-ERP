package com.cherry.erp.modules.quality.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FNCAnomaliesCountByMonthDTO {
    private Integer month;
    private Long nc;
    private Long rebut;
    private Long cout;
    private Long fncCount;
}
