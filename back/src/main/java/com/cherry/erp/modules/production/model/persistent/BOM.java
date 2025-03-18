package com.cherry.erp.modules.production.model.persistent;

import com.cherry.erp.common.model.persistent.GenericEntity;
import com.cherry.erp.modules.production.model.enums.ManufacturingTypeEnum;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "bom")
@SequenceGenerator(name = "SEQ", sequenceName = "bom_seq", allocationSize = 1)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class BOM extends GenericEntity {
    private static final long serialVersionUID = 1307346987174803693L;

    @Column(name = "reference")
    private String reference;
    private String indice;
    private String designation;

    @Enumerated(EnumType.STRING)
    private ManufacturingTypeEnum manufacturingType;

    private String[] manufacturingProcesses ;








}

