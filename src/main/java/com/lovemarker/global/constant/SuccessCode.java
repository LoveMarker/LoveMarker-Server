package com.lovemarker.global.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum SuccessCode {

    // 200
    LOGIN_SUCCESS(HttpStatus.OK, "로그인에 성공했습니다."),

    // 201
    CREATE_INVITATION_CODE_SUCCESS(HttpStatus.CREATED, "초대 코드 생성을 성공했습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    public int getHttpStatusCode() {
        return httpStatus.value();
    }
}
