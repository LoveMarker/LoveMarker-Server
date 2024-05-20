package com.lovemarker.domain.user.exception;

import com.lovemarker.global.constant.ErrorCode;
import com.lovemarker.global.exception.NotFoundException;

public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
