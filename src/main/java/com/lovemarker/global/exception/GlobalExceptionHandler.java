package com.lovemarker.global.exception;

import com.lovemarker.global.constant.ErrorCode;
import com.lovemarker.global.dto.ApiResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ApiResponseDto<Object> handleException(final Exception error, final HttpServletRequest request) {
        return ApiResponseDto.error(ErrorCode.INTERNAL_SERVER_ERROR);
    }
}
