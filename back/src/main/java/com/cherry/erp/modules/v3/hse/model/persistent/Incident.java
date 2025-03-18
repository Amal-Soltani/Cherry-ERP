package com.cherry.erp.modules.v3.hse.model.persistent;

import com.cherry.erp.common.model.JsonbType;
import com.cherry.erp.common.model.persistent.GenericEntity;
import com.cherry.erp.company.model.persistent.Company;
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
@Table(name = "incident")
@SequenceGenerator(name = "SEQ", sequenceName = "incident_seq", allocationSize = 1)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "is_active = true")
@SQLDelete(sql = "UPDATE incident SET is_active = false WHERE id = ? AND version = ?")
@TypeDefs({
        @TypeDef(typeClass = IntArrayType.class, defaultForType = Integer[].class),
        @TypeDef(name = "JsonbType", typeClass = JsonbType.class),
        @TypeDef(name = "string-array", typeClass = StringArrayType.class)
})
public class Incident extends GenericEntity {

    private static final long serialVersionUID = 1307346987174803693L;

    @Column(name = "reference")
    private String reference;

    @Column(name = "date")
    private Date date;

    @Column(name = "department")
    private Integer department;

    @Column(name = "description")
    private String description;

    @Column(name = "reporter") // EMPLOYEE ID
    private Integer reporterId;

    @Column(name = "totalInjury")
    private Integer totalInjury;

    @Column(name = "injuries_ids", columnDefinition = "integer[]") // [EmployeeId]
    private Integer[] injuries = new Integer[0];

    @Column(name = "damaged_equipment_id")
    private Integer damagedEquipmentId;

    @Column(name = "estimatedLoss")
    private Double estimatedLoss;

    @Type(type = "text")
    @Column(name = "image")
    private String image;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "COMPANY_ID")
    private Company company;
}
