package com.cherry.erp.administration.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

import static javax.persistence.GenerationType.SEQUENCE;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class SimpleEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue( strategy = SEQUENCE, generator = "SEQ")
	protected Integer id = 0;

	@Version
	@Column(columnDefinition = "integer DEFAULT 0")
	protected Integer version;

	public SimpleEntity() { }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public long getVersion() {
		return version!=null?version:0;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}
