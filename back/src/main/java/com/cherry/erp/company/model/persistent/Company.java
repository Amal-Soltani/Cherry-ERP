package com.cherry.erp.company.model.persistent;

import com.cherry.erp.common.model.persistent.GenericEntity;
import com.cherry.erp.company.model.enums.CompanyOrganisationTypeEnum;
import com.cherry.erp.company.model.enums.CompanySizeEnum;
import com.cherry.erp.company.model.enums.SupportEnum;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "COMPANIES")
@SequenceGenerator(name = "SEQ", sequenceName = "companies_seq", allocationSize = 1)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Where(clause = "is_active = true")
@SQLDelete(sql = "UPDATE COMPANIES SET is_active = false WHERE id = ? AND version = ?")
public class Company extends GenericEntity {

    private static final long serialVersionUID = 1090088612933845086L;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "SECTOR")
    private String sector;

    @Column(name = "PAYS")
    private String pays;

    @Enumerated(EnumType.STRING)
    private CompanySizeEnum size;

    @Enumerated(EnumType.STRING)
    private CompanyOrganisationTypeEnum type;

    @Column(name = "LOGO")
    @Type(type = "text")
    private String logo;

    @Column(name = "slogan")
    private String slogan;

    @Column(name = "ADRESSE")
    private String adresse;

    @Column(name = "REGISTRE_DE_COMMERCE")
    private String registreDeCommerce;

    @Column(name = "MATRICULE_FISCALE")
    private String matriculeFiscale;

    @Column(name = "CODE_DOUANE")
    private String codeDouane;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "TEL")
    private String tel;

    @Column(name = "FAX")
    private String fax;

    @Column(name = "MOBILE")
    private String mobile;

    @Column(name = "SUPPORT")
    private SupportEnum support; //type de support: silver, gold, platinum

    @Column(name = "language")
    private String language;

    @Column(name = "theme")
    private String theme;

    @Column(name = "localize")
    private Boolean localize;

    @Column(name = "payroll_in_progress")
    private Boolean payrollInProgress;

}
