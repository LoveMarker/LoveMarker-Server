package com.lovemarker.global.exception;

import com.lovemarker.global.constant.ErrorCode;

public abstract class BasicException extends RuntimeException {

    private final ErrorCode errorCode;

    protected BasicException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public int getErrorStatusCode() {
        return errorCode.getHttpStatusCode();
    }
}
