package com.lovemarker.global.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode {

    // 400
    REQUEST_VALIDATION_EXCEPTION(HttpStatus.BAD_REQUEST),

    // 404
    NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND),

    // 500
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR);

    private final HttpStatus httpStatus;

    public int getHttpStatusCode() {
        return httpStatus.value();
    }
}
