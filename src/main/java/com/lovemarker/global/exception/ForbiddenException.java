package com.lovemarker.global.exception;

import com.lovemarker.global.constant.ErrorCode;

public class ForbiddenException extends BasicException {

    public ForbiddenException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
