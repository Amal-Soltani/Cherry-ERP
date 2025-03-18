package com.cherry.erp.modules.gmao.model.persistent;

import com.cherry.erp.common.model.persistent.GenericEntity;
import com.cherry.erp.company.model.persistent.Company;
import com.cherry.erp.modules.gmao.model.enums.CategoryEnum;
import com.cherry.erp.modules.gmao.model.enums.StateEnum;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "equipment")
@SequenceGenerator(name = "SEQ", sequenceName = "equipment_seq", allocationSize = 1)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Equipment extends GenericEntity {
    private static final long serialVersionUID = 1307346987174803693L;

    @Column(name = "reference")
    private String reference;
    private String name;
    private String emplacement;
    @Temporal(TemporalType.DATE)
    private Date purchaseDate;
    @Temporal(TemporalType.DATE)
    private Date releaseDate;
    private double purchasePrice;

    private String description;
    @Enumerated(EnumType.STRING)
    private StateEnum stateEnum;
    @Enumerated(EnumType.STRING)
    private CategoryEnum categoryEnum;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "COMPANY_ID")
    private Company company;



}
