package com.cherry.erp.modules.production.model.persistent;

import com.cherry.erp.modules.stock.model.persistent.Article;
import lombok.*;

import javax.persistence.*;
import static javax.persistence.GenerationType.SEQUENCE;


@Entity
@Table(name = "raw_material")
@SequenceGenerator(name = "SEQ", sequenceName = "raw_material_seq", allocationSize = 1)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RawMaterial  {
    private static final long serialVersionUID = 1307346987174803693L;
    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "SEQ")
    protected Integer id = 0;
    private String rawDimension;
    private double grossQuantity;
    @ManyToOne
    private Article article;

    @ManyToOne (cascade = CascadeType.REMOVE)
    private Product product;
}
