package com.cherry.erp.common.utils;

import com.cherry.erp.common.model.dto.GenericResponse;
import com.cherry.erp.common.model.enums.ResponseRestMsgCodeEnum;

public class ResponseFactory {

    private ResponseFactory() {
        throw new IllegalStateException("Utility Class");
    }

    public static GenericResponse success(String msg, Object res) {
        return new GenericResponse(msg, res, "");
    }

    public static GenericResponse success(ResponseRestMsgCodeEnum msg, Object res) {
        return new GenericResponse(msg.getCode(), res, "");
    }

    public static GenericResponse failure(String errorMsg, String moreInf) {
        return new GenericResponse(errorMsg, null, moreInf);
    }

    public static GenericResponse failure(ResponseRestMsgCodeEnum errorMsg, String moreInf) {
        return new GenericResponse(errorMsg.getCode(), null, moreInf);
    }

    public static GenericResponse failure(String errorMsg, Object res, String moreInf) {
        return new GenericResponse(errorMsg, res, moreInf);
    }

    public static GenericResponse failure(ResponseRestMsgCodeEnum errorMsg, Object res, String moreInf) {
        return new GenericResponse(errorMsg.getCode(), res, moreInf);
    }

}
