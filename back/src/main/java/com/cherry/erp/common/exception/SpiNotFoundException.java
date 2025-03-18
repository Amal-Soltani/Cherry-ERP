package com.cherry.erp.common.exception;

import org.springframework.http.HttpStatus;

public class SpiNotFoundException extends SpiException {
    public SpiNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
