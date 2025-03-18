package com.cherry.erp.company.model.dto;

import com.cherry.erp.common.model.dto.GenericDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class MinimalCompanyDto extends GenericDto implements Serializable {

    private String name;

    private String adresse;

    private String tel;

}
