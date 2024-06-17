package com.lovemarker.domain.auth.exception;

import com.lovemarker.global.constant.ErrorCode;
import com.lovemarker.global.exception.BasicException;

public class TimeExpiredTokenException extends BasicException {

    public TimeExpiredTokenException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
