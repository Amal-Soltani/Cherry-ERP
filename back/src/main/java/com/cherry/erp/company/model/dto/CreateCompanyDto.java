package com.cherry.erp.company.model.dto;

import com.cherry.erp.company.model.enums.CompanyOrganisationTypeEnum;
import com.cherry.erp.company.model.enums.CompanySizeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class CreateCompanyDto implements Serializable {

    private String name;

    private String tradeName;

    private String sector;

    private String pays;

    private CompanySizeEnum size;

    private CompanyOrganisationTypeEnum type;

    private String adresse;

    private String tel;

    private String firstName;

    private String lastName;

    private String email;

    /* begin license */
    private Date startDate;

    private Date endDate;

    private Boolean expired;

    private String[] modules = new String[0];

    private Integer nbUsers;

    private Integer nbDocuments;

    private Integer storage;
    /* end license */

}
