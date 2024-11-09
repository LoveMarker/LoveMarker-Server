package com.lovemarker.global.image.exception;

import com.lovemarker.global.constant.ErrorCode;
import com.lovemarker.global.exception.BasicException;

public class S3BadRequestException extends BasicException {

    public S3BadRequestException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
