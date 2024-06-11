package com.lovemarker.domain.memory.exception;

import com.lovemarker.global.constant.ErrorCode;
import com.lovemarker.global.exception.NotFoundException;

public class MemoryNotFoundException extends NotFoundException {

    public MemoryNotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
