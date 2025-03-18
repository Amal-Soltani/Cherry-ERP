package com.cherry.erp.modules.v2.hr.model.dto;

import com.cherry.erp.common.model.dto.GenericDto;
import com.cherry.erp.company.model.persistent.Company;
import com.cherry.erp.modules.v2.hr.model.enums.FamilySituationEnum;
import com.cherry.erp.modules.v2.hr.model.enums.PayModelEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto extends GenericDto {

    private String employeeNumber; //matricule de l'employé

    private String firstName;

    private String lastName;

    private String post;

    private String phone;

    private String image;

    private String imageUrl;

    private String emailPro;

    private String emailPerso;

    private Date birthDate;

    private Boolean titulaire;

    private FamilySituationEnum familySituation;

    private Integer children;

    private Date hiringDate;

    private Date leavingDate;

    private Integer managerId; // id of Employee

    private Integer gradeId; // id of EmployeeGrade

    private BasicEmployeeDto manager;

    private Boolean external; // TRUE for external collaborators

    private String externalType; // SUPPLIER, CLIENT

    private Integer partnerId; // supplier or client id

    private Set<String> courses = new HashSet<>();

    private Company company;


}
