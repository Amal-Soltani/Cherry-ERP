package com.cherry.erp.modules.v3.hse.model.persistent;

import com.cherry.erp.common.model.JsonbType;
import com.cherry.erp.common.model.persistent.GenericEntity;
import com.cherry.erp.company.model.persistent.Company;
import com.cherry.erp.modules.v2.hr.model.persistent.Employee;
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
import java.util.List;

@Entity
@Table(name = "training_session")
@SequenceGenerator(name = "SEQ", sequenceName = "training_session_seq", allocationSize = 1)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "is_active = true")
@SQLDelete(sql = "UPDATE training_session SET is_active = false WHERE id = ? AND version = ?")
@TypeDefs({
        @TypeDef(typeClass = IntArrayType.class, defaultForType = Integer[].class),
        @TypeDef(name = "JsonbType", typeClass = JsonbType.class),
        @TypeDef(name = "string-array", typeClass = StringArrayType.class)
})
public class TrainingSession extends GenericEntity {

    private static final long serialVersionUID = 1307346987174803693L;

    @Column(name = "reference")
    private String reference;

    @OneToOne
    @JoinColumn(name = "subject")
    private Training subject;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_date")
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "participants_ids", columnDefinition = "integer[]") // [EmployeeId]
    private Integer[] participants = new Integer[0];

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "COMPANY_ID")
    private Company company;
}
