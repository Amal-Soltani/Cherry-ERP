package com.cherry.erp.account.security.domain;

import com.cherry.erp.account.model.dto.UserDto;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class AuthenticationResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;

    private UserDto user;
    private String token;
    private Integer loggedAs;

}

