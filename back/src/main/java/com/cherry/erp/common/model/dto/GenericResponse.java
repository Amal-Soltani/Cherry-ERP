package com.cherry.erp.common.model.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class GenericResponse implements Serializable {

    /**
     * message :
     * The message property is a nice human readable error message that can potentially be shown directly to an application end user (not a developer).  It should be friendly and easy to understand and convey a concise reason as to why the error occurred.  It should probaby not contain technical information.
     * Technical information should be in the developerMessage property instead (covered next).
     */
    private String message = "";  //ops! It looks like that file does not exist.

    /**
     * moreInfo :
     * The moreInfo property specifies a URL that anyone seeing the error message can click (or copy and paste) in a browser.
     * The target web page should describe the error condition fully,as well as potential solutions to help them resolve the error condition.
     */
    private String moreInfo = ""; //The moreInfo property specifies a URL that anyone seeing the error message can click (or copy and paste) in a browser.

    private Object result = null;

    private Date timestamp;

    public GenericResponse(String msg, Object res, String moreInf) {
        this.message = msg;
        this.result = res;
        this.moreInfo = moreInf;
        this.timestamp = new Date();
    }
}
