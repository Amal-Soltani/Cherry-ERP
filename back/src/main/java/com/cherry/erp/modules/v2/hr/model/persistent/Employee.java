package com.cherry.erp.modules.v2.hr.model.persistent;

import com.cherry.erp.common.model.persistent.GenericEntity;
import com.cherry.erp.company.model.persistent.Company;
import com.cherry.erp.modules.projet.model.persistent.Tache;
import com.cherry.erp.modules.v2.hr.model.enums.FamilySituationEnum;
import com.cherry.erp.modules.v2.hr.model.enums.PayModelEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.*;

@Entity
@Table(name = "employees")
@SequenceGenerator(name = "SEQ", sequenceName = "employees_seq", allocationSize = 1)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "is_active = true")
@SQLDelete(sql = "UPDATE employees SET is_active = false WHERE id = ? AND version = ?")
public class Employee extends GenericEntity {

    private static final long serialVersionUID = 1307346987174803693L;

    @Column(name = "employee_number", nullable = false)
    private String employeeNumber; //matricule de l'employé

    @Column(name = "first_name")
    @NotBlank(message = "lastName is required")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank(message = "lastName is required")
    private String lastName;

    @Column(name = "post")
    private String post;

    @Column(name = "phone")
    private String phone;

    @Type(type = "text")
    @Column(name = "image")
    private String image;

    private String imageUrl;

    private String emailPro;

    private String emailPerso;

    private Date birthDate;

    private Boolean titulaire;

    @Enumerated(EnumType.STRING)
    private FamilySituationEnum familySituation;

    private Integer children;

    private Date hiringDate;

    private Date leavingDate;

    private Integer managerId; // id of Employee

    private Integer gradeId; // id of EmployeeGrade

    @Enumerated(EnumType.STRING)
    private PayModelEnum payModel; // KAD

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "COMPANY_ID")
    private Company company;

    private Boolean external; // TRUE for external collaborators

    private String externalType; // SUPPLIER, CLIENT

    private Integer partnerId; // supplier or client id

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }


}
