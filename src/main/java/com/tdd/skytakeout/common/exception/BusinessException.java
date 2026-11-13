package com.tdd.skytakeout.common.exception;

import org.springframework.http.HttpStatus;

public class BusinessException extends RuntimeException {

    private final HttpStatus httpStatus;//HTTP 协议状态
    private final int code; //项目自己的业务错误码
    // message 供前端展示，传递给父类
    public BusinessException(
            HttpStatus httpStatus,
            int code,
            String message
    ) {
        super(message);
        this.httpStatus = httpStatus;
        this.code = code;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public int getCode() {
        return code;
    }
}