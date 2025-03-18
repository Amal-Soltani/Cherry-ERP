package com.cherry.erp.common.exception;

import com.cherry.erp.common.model.enums.ResponseRestMsgCodeEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class SpiException extends RuntimeException {

  private HttpStatus httpStatus;
  private String code;
  private String message;

  public SpiException(HttpStatus httpStatus, ResponseRestMsgCodeEnum restMsg) {
    super(restMsg.getCode());
    this.code = restMsg.getCode();
    this.httpStatus = httpStatus;
  }

  public SpiException(HttpStatus httpStatus, String code) {
    super(code);
    this.code = code;
    this.httpStatus = httpStatus;
  }

  public SpiException(HttpStatus httpStatus, ResponseRestMsgCodeEnum restMsg, String message) {
    super(restMsg.getCode());
    this.message = message;
    this.code = code;
    this.httpStatus = httpStatus;
  }


}
