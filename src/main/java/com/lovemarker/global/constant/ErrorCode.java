package com.lovemarker.global.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode {

    // 400
    REQUEST_VALIDATION_EXCEPTION(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    MISSING_REQUIRED_INFO_EXCEPTION(HttpStatus.BAD_REQUEST, "필수 입력 항목이 입력되지 않았습니다."),
    MISSING_REQUIRED_HEADER_EXCEPTION(HttpStatus.BAD_REQUEST, "필수 입력 헤더 정보가 입력되지 않았습니다."),

    // 404
    NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "존재하지 않는 리소스입니다."),

    // 500
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류 발생");

    private final HttpStatus httpStatus;
    private final String message;

    public int getHttpStatusCode() {
        return httpStatus.value();
    }
}
