package com.cherry.erp.administration.model.persistent;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResponseRestMessage implements Serializable {

    private String locale;

    private String message;

    private Boolean defaultMsg;

}
