package com.cherry.erp.modules.v3.hse.model.persistent;

import com.cherry.erp.common.model.JsonbType;
import com.cherry.erp.common.model.persistent.GenericEntity;
import com.cherry.erp.company.model.persistent.Company;
import com.cherry.erp.modules.v3.hse.model.enums.PermitWorkTypeEnum;
import com.vladmihalcea.hibernate.type.array.IntArrayType;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "permit_work")
@SequenceGenerator(name = "SEQ", sequenceName = "permit_work_seq", allocationSize = 1)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "is_active = true")
@SQLDelete(sql = "UPDATE permit_work SET is_active = false WHERE id = ? AND version = ?")
@TypeDefs({
        @TypeDef(typeClass = IntArrayType.class, defaultForType = Integer[].class),
        @TypeDef(name = "JsonbType", typeClass = JsonbType.class),
        @TypeDef(name = "string-array", typeClass = StringArrayType.class)
})
public class PermitWork extends GenericEntity {

    private static final long serialVersionUID = 1307346987174803693L;

    @Column(name = "reference")
    private String reference;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private PermitWorkTypeEnum type;

    @OneToOne
    @JoinColumn(name = "correctiveAction")
    private CorrectiveAction correctiveAction;

    @Column(name = "initiator_id") // Employee ID
    private Integer initiator;

    @Column(name = "executors_ids", columnDefinition = "integer[]") // [EmployeeId]
    private Integer[] executors = new Integer[0];

    @Column(name = "description")
    private String description;

    @Column(name = "risks")
    private String risks;

    @Column(name = "start_date")
    private Date startDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "COMPANY_ID")
    private Company company;
}
