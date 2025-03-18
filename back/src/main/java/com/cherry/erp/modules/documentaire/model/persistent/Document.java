package com.cherry.erp.modules.documentaire.model.persistent;

import com.cherry.erp.common.model.persistent.GenericEntity;
import com.cherry.erp.modules.production.model.persistent.Product;
import lombok.*;


import javax.persistence.*;

@Entity
@Table(name = "document")
@SequenceGenerator(name = "SEQ", sequenceName = "document_seq", allocationSize = 1)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Document extends GenericEntity {
    private static final long serialVersionUID = 1307346987174803693L;


    private String reference;
    private String name;
    private String indice;
    private double size;
    private String type;
    private byte[] data;
    @ManyToOne
    private Product product;



}
