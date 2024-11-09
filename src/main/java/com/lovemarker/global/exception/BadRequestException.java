package com.lovemarker.global.exception;

import com.lovemarker.global.constant.ErrorCode;

public class BadRequestException extends BasicException {

    public BadRequestException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
