package com.lovemarker.global.dto;

import com.lovemarker.global.constant.ErrorCode;
import com.lovemarker.global.constant.SuccessCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponseDto<T> {

    private final int status;
    private final Boolean success;
    private final String message;
    private T data;

    public static ApiResponseDto success(SuccessCode successCode) {
        return new ApiResponseDto<>(successCode.getHttpStatusCode(), true, successCode.getMessage());
    }

    public static <T> ApiResponseDto<T> success(SuccessCode successCode, T data) {
        return new ApiResponseDto<T>(successCode.getHttpStatusCode(), true, successCode.getMessage(), data);
    }

    public static ApiResponseDto error(ErrorCode errorCode) {
        return new ApiResponseDto<>(errorCode.getHttpStatusCode(), false, errorCode.getMessage());
    }

    public static ApiResponseDto error(ErrorCode errorCode, String message) {
        return new ApiResponseDto<>(errorCode.getHttpStatusCode(), false, message);
    }
}

