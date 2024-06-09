package com.lovemarker.domain.auth.exception;

import com.lovemarker.global.constant.ErrorCode;
import com.lovemarker.global.exception.BasicException;

public class UnauthorizedException extends BasicException {

    public UnauthorizedException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
