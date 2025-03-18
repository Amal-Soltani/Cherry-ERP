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
import java.util.Date;

@Entity
@Table(name = "corrective_action")
@SequenceGenerator(name = "SEQ", sequenceName = "corrective_action_seq", allocationSize = 1)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "is_active = true")
@SQLDelete(sql = "UPDATE corrective_action SET is_active = false WHERE id = ? AND version = ?")
public class CorrectiveAction extends GenericEntity {

    private static final long serialVersionUID = 1307346987174803693L;

    @Column(name = "reference")
    private String reference;

    @Column(name = "department")
    private String department;//responsible for execution of action

    @Column(name = "description")
    private String description;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "progress")
    private Integer progress;

    @ManyToOne
    @JoinColumn(name = "plan_action_id")
    private PlanAction planAction;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "COMPANY_ID")
    private Company company;
}
