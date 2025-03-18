package com.cherry.erp.company.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class BasicCompanyDto implements Serializable {

    private Integer id;

    private String name;

}
