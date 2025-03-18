package com.cherry.erp.common.exception.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.List;

@Data
public class SpiExceptionDto {
    private Date timestamp;
    private HttpStatus status;
    private String message;
    private String error;
    private List<String> errors;

    public SpiExceptionDto(HttpStatus status, String message, List<String> errors) {
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
        this.timestamp = new Date();
    }

    public SpiExceptionDto(HttpStatus status, String message, String error) {
        super();
        this.status = status;
        this.message = message;
        this.error = error;
        this.timestamp = new Date();
    }
}