package com.cherry.erp.company.model.dto;

import com.cherry.erp.common.model.dto.GenericDto;
import com.cherry.erp.company.model.enums.CompanyOrganisationTypeEnum;
import com.cherry.erp.company.model.enums.CompanySizeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class CompanyDto extends GenericDto implements Serializable {

    private String name;

    private String sector;

    private String pays;

    private CompanySizeEnum size;

    private CompanyOrganisationTypeEnum type;

    private String logo;

    private String slogan;

    private String adresse;

    private String registreDeCommerce;

    private String matriculeFiscale;

    private String codeDouane;

    private String email;

    private String tel;

    private String fax;

    private String mobile;

    private String defaultCurrency;

    private Integer nbUsers;

    private Date lastActivity;

    private String support;

    private int sla;

    private String language;

    private String theme;

    private Boolean localize;

    private Boolean payrollInProgress;

}
