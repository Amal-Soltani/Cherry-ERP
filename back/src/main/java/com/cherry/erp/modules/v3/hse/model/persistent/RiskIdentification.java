package com.cherry.erp.modules.v3.hse.model.persistent;

import com.cherry.erp.common.model.persistent.GenericEntity;
import com.cherry.erp.company.model.persistent.Company;
import com.cherry.erp.modules.v2.hr.model.persistent.Employee;
import com.cherry.erp.modules.v3.hse.model.enums.PermitWorkTypeEnum;
import com.cherry.erp.modules.v3.hse.model.enums.RiskIdentificationPriorityEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "risk_identification")
@SequenceGenerator(name = "SEQ", sequenceName = "risk_identification_seq", allocationSize = 1)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "is_active = true")
@SQLDelete(sql = "UPDATE risk_identification SET is_active = false WHERE id = ? AND version = ?")

public class RiskIdentification extends GenericEntity {
    private static final long serialVersionUID = 1307346987174803693L;

    @Column(name = "reference")
    private String reference;

    @Column(name ="date")
    private Date date;

    @Column(name = "initiator_id")
    private Integer initiator;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private RiskIdentificationPriorityEnum priority;

    @Column(name = "description")
    private String description;

    @Type(type = "text")
    @Column(name = "image")
    private String image;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "COMPANY_ID")
    private Company company;
}
