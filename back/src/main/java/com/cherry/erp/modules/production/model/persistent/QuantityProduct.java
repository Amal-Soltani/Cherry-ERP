package com.cherry.erp.modules.production.model.persistent;

import com.cherry.erp.common.model.persistent.GenericEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "quantity_product")
@SequenceGenerator(name = "SEQ", sequenceName = "quantity_product_seq", allocationSize = 1)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuantityProduct extends GenericEntity {
    private static final long serialVersionUID = 1307346987174803693L;

    private Integer qteParAssemblage;

    @ManyToOne
    private Product parent;

    @ManyToOne
    private Product child;
}
