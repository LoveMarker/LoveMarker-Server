package com.lovemarker.global.exception;

import com.lovemarker.global.constant.ErrorCode;

public class NotFoundException extends BasicException {

    public NotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
