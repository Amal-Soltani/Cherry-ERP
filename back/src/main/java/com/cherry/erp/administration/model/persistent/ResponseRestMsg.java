package com.cherry.erp.administration.model.persistent;

import com.cherry.erp.common.model.persistent.GenericEntity;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "response_rest_msg")
@SequenceGenerator(name = "SEQ", sequenceName = "response_rest_msg_seq", allocationSize = 1)
@Getter
@Setter
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Where(clause = "is_active = true")
@SQLDelete(sql = "UPDATE response_rest_msg SET is_active = false WHERE id = ? AND version = ?")
public class ResponseRestMsg extends GenericEntity {

    @Column(name = "code", unique = true)
    private String code;

    @Type(type = "jsonb")
    @Column(name = "messages", columnDefinition = "jsonb")
    private ResponseRestMessages messages;

}
