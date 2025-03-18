package com.cherry.erp.modules.v3.hse.model.persistent;

import com.cherry.erp.common.model.persistent.GenericEntity;
import com.cherry.erp.company.model.persistent.Company;
import com.cherry.erp.modules.v3.hse.model.enums.PermitWorkTypeEnum;
import com.cherry.erp.modules.v3.hse.model.enums.PlanActionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "plan_action")
@SequenceGenerator(name = "SEQ", sequenceName = "plan_action_seq", allocationSize = 1)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "is_active = true")
@SQLDelete(sql = "UPDATE plan_action SET is_active = false WHERE id = ? AND version = ?")
public class PlanAction extends GenericEntity {

    private static final long serialVersionUID = 1307346987174803693L;

    @Column(name = "reference")
    private String reference;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private PlanActionEnum type;

    @Column(name = "trigger_id") // id de l'incident ou riskAssessment ou FNC ....s
    private Integer triggerId;

    @Column(name = "trigger_ref") // le référence de l'incident ou riskAssessment ou FNC ....s
    private String triggerRef;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "COMPANY_ID")
    private Company company;
}
