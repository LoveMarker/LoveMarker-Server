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

    public static ApiResponseDto success(SuccessCode successStatus) {
        return new ApiResponseDto<>(successStatus.getHttpStatusCode(), true, successStatus.getMessage());
    }

    public static <T> ApiResponseDto<T> success(SuccessCode successStatus, T data) {
        return new ApiResponseDto<T>(successStatus.getHttpStatusCode(), true, successStatus.getMessage(), data);
    }

    public static ApiResponseDto error(ErrorCode errorStatus) {
        return new ApiResponseDto<>(errorStatus.getHttpStatusCode(), false, errorStatus.getMessage());
    }

    public static ApiResponseDto error(ErrorCode errorStatus, String message) {
        return new ApiResponseDto<>(errorStatus.getHttpStatusCode(), false, message);
    }
}

