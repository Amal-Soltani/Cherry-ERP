package com.cherry.erp.account.model.dto;

import com.cherry.erp.common.model.dto.GenericDto;
import com.cherry.erp.modules.v2.hr.model.dto.EmployeeDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto extends GenericDto {

    private EmployeeDto employee;
    private String email;
    private boolean enabled;
    private String[] roles;
    private Integer[] stores;
    private Integer connection;
    private Date lastConnexion;
    private Integer[] equipes = new Integer[0];

}
