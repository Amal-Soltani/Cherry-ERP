package com.cherry.erp.common.exception;

import com.cherry.erp.common.model.enums.ResponseRestMsgCodeEnum;
import org.springframework.http.HttpStatus;

public class ActionNotAllowedException extends SpiException {
    public ActionNotAllowedException() {
        super(HttpStatus.BAD_REQUEST, ResponseRestMsgCodeEnum.ACTION_NOT_ALLOWED);
    }

}
