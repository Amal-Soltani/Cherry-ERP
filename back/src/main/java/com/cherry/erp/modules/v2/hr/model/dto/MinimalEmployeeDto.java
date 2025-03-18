package com.cherry.erp.modules.v2.hr.model.dto;

import com.cherry.erp.common.model.dto.GenericDto;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MinimalEmployeeDto extends GenericDto implements Serializable {

    private String firstName;

    private String lastName;

}
