package com.cherry.erp.modules.v3.hse.model.persistent;

import com.cherry.erp.common.model.persistent.GenericEntity;
import com.cherry.erp.company.model.persistent.Company;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "risk_assessment")
@SequenceGenerator(name = "SEQ", sequenceName = "risk_assessment_seq", allocationSize = 1)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "is_active = true")
@SQLDelete(sql = "UPDATE risk_assessment SET is_active = false WHERE id = ? AND version = ?")
public class RiskAssessment extends GenericEntity {

    private static final long serialVersionUID = 1307346987174803693L;

    @Column(name = "reference")
    private String reference;

    @Column(name = "responsable_id")
    private Integer responsableId;

    @Column(name = "operation")
    private String operation;

    @Column(name = "risks")
    private String risks;

    @Column(name = "impacted") // les ressources/env/employees impactés
    private String impacted;

    @Type(type = "text")
    @Column(name = "image")
    private String image;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "COMPANY_ID")
    private Company company;
}
