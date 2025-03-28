package com.cherry.erp.account.security.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class AuthenticationRequest implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    private String username;
    private String password;

    private Double longitude;
    private Double latitude;

}
