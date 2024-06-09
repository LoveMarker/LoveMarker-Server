package com.lovemarker.domain.user.exception;

import com.lovemarker.global.constant.ErrorCode;
import com.lovemarker.global.exception.BasicException;

public class UserNicknameDuplicateException extends BasicException {

    public UserNicknameDuplicateException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
