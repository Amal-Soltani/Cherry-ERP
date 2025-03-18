package com.cherry.erp.modules.v2.hr.model.dto;

import com.cherry.erp.common.model.dto.GenericDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BasicEmployeeDto extends GenericDto {

    private String employeeNumber; //matricule de l'employé

    private String firstName;

    private String lastName;

    private String post;

    private String phone;

    private String imageUrl;

}
