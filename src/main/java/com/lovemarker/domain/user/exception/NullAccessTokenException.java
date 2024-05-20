package com.lovemarker.domain.user.exception;

import com.lovemarker.global.constant.ErrorCode;
import com.lovemarker.global.exception.BasicException;

public class NullAccessTokenException extends BasicException {
    public NullAccessTokenException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
