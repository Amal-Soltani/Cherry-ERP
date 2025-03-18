package com.cherry.erp.company.model.dto;

import com.cherry.erp.company.model.enums.BusinessPlanEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class RegisterCompanyDto implements Serializable {

    private String name;

    private String adresse;

    private String tel;

    private String firstName;

    private String lastName;

    private String email;

    private BusinessPlanEnum plan;

}
