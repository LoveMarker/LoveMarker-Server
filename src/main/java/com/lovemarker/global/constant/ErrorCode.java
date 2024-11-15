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
    NULL_ACCESS_TOKEN_EXCEPTION(HttpStatus.BAD_REQUEST, "토큰 값이 없습니다."),
    DUPLICATE_NICKNAME_EXCEPTION(HttpStatus.BAD_REQUEST, "중복된 닉네임입니다."),
    IMAGE_VALIDATION_EXCEPTION(HttpStatus.BAD_REQUEST, "잘못된 파일입니다."),

    // 401
    ACCESS_TOKEN_TIME_EXPIRED_EXCEPTION(HttpStatus.UNAUTHORIZED, "만료된 액세스 토큰입니다."),
    REFRESH_TOKEN_TIME_EXPIRED_EXCEPTION(HttpStatus.UNAUTHORIZED, "만료된 리프레시 토큰입니다."),
    INVALID_REFRESH_TOKEN_EXCEPTION(HttpStatus.UNAUTHORIZED, "유효하지 않은 리프레시 토큰입니다."),
    INVALID_ACCESS_TOKEN_EXCEPTION(HttpStatus.UNAUTHORIZED, "유효하지 않은 엑세스 토큰입니다."),
    INVALID_GOOGLE_ID_TOKEN_EXCEPTION(HttpStatus.UNAUTHORIZED, "유효하지 않은 구글 아이디 토큰입니다."),

    // 403
    NO_COUPLE_FORBIDDEN_EXCEPTION(HttpStatus.FORBIDDEN, "커플 기능에 접근할 수 없습니다."),

    // 404
    NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "존재하지 않는 리소스입니다."),
    NOT_FOUND_USER_EXCEPTION(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다"),
    NOT_FOUND_MEMORY_EXCEPTION(HttpStatus.NOT_FOUND, "존재하지 않는 추억입니다."),

    // 500
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류 발생");

    private final HttpStatus httpStatus;
    private final String message;

    public int getHttpStatusCode() {
        return httpStatus.value();
    }
}
