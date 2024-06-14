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
    UPDATE_USER_NICKNAME_SUCCESS(HttpStatus.OK, "닉네임 변경에 성공했습니다."),
    FIND_MY_PAGE_SUCCESS(HttpStatus.OK, "마이페이지 조회에 성공했습니다."),
    FIND_MEMORY_DETAIL_SUCCESS(HttpStatus.OK, "추억 상세 조회에 성공했습니다."),
    FIND_MEMORY_LIST_SUCCESS(HttpStatus.OK, "추억 리스트뷰 조회에 성공했습니다."),

    // 201
    CREATE_INVITATION_CODE_SUCCESS(HttpStatus.CREATED, "초대 코드 생성을 성공했습니다."),
    JOIN_COUPLE_SUCCESS(HttpStatus.CREATED, "커플 연결을 성공했습니다."),
    CREATE_MEMORY_SUCCESS(HttpStatus.CREATED, "추억 생성을 성공했습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    public int getHttpStatusCode() {
        return httpStatus.value();
    }
}
