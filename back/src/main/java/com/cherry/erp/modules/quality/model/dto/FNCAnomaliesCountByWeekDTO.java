package com.cherry.erp.modules.quality.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FNCAnomaliesCountByWeekDTO {
    private Integer week;
    private Long nc;
    private Long rebut;
    private Long cout;
    private Long fncCount;
}
