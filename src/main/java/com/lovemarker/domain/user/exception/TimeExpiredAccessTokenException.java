package com.lovemarker.domain.user.exception;

import com.lovemarker.global.constant.ErrorCode;
import com.lovemarker.global.exception.BasicException;

public class TimeExpiredAccessTokenException extends BasicException {
    public TimeExpiredAccessTokenException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
