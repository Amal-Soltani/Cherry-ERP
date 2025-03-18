package com.cherry.erp.administration.model.persistent;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResponseRestMessages implements Serializable {

    private List<ResponseRestMessage> messages;

}
