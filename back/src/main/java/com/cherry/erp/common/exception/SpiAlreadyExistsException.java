package com.cherry.erp.common.exception;

import org.springframework.http.HttpStatus;

import static com.cherry.erp.common.model.enums.ResponseRestMsgCodeEnum.ALREADY_EXISTS;

public class SpiAlreadyExistsException extends SpiException {
    public SpiAlreadyExistsException() {
        super(HttpStatus.BAD_REQUEST, ALREADY_EXISTS);
    }

}
