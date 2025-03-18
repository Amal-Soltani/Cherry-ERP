package com.cherry.erp.company.model.persistent;

import com.cherry.erp.common.model.persistent.GenericEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "cherry_ref_configs",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"company_id", "object", "year"})
        })
@SequenceGenerator(name = "SEQ", sequenceName = "cherry_REF_CONFIGS_SEQ", allocationSize = 1)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"company"})
public class ReferenceConfig extends GenericEntity {

    @Column(name = "object", nullable = false)
    private String object; // class name

    @Column(name = "year")
    private Integer year;

    @Column(name = "number", columnDefinition = "integer DEFAULT 0")
    private Integer number;

    @Column(name = "prefix")
    private String prefix;

    @Column(name = "number_format", nullable = false)
    private String numberFormat;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "company_id", updatable = false)
    private Company company;

}
