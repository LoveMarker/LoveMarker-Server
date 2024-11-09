package com.lovemarker.domain.auth.exception;

import com.lovemarker.global.constant.ErrorCode;
import com.lovemarker.global.exception.BasicException;

public class InvalidTokenException extends BasicException {

    public InvalidTokenException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
