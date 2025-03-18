package com.cherry.erp.common.model.persistent;

import com.cherry.erp.common.model.enums.GenericParameterEnum;
import com.cherry.erp.company.model.persistent.Company;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "generic_parameters")
@SequenceGenerator(name = "SEQ", sequenceName = "generic_parameters_seq", allocationSize = 1)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Where(clause = "is_active = true")
@SQLDelete(sql = "UPDATE generic_parameters SET is_active = false WHERE id = ? AND version = ?")
public class GenericParameter extends GenericEntity {
    
    private Boolean stockShowAlertInFeed = true;

    private Boolean sendMailForStockAlert = true;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "COMPANY_ID")
    private Company company;

}
