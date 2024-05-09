package com.lovemarker.domain.user.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

import com.lovemarker.global.exception.BadRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class UserNicknameTest {

    @Nested
    @DisplayName("UserNickname 생성 시")
    class NewUserNicknameTest {

        @Test
        @DisplayName("성공")
        void newUserNickname() {
            //given
            String nickname = "nickname";

            //when
            UserNickname userNickname = new UserNickname(nickname);

            //then
            assertThat(userNickname.getNickname()).isEqualTo(nickname);
        }

        @Test
        @DisplayName("예외(BadRequestException): 입력값이 null")
        void exceptionWhenNicknameIsNull() {
            //given
            String nickname = null;

            //when
            Exception exception = catchException(() -> new UserNickname(nickname));

            //then
            assertThat(exception).isInstanceOf(BadRequestException.class);
        }

        @Test
        @DisplayName("예외(BadRequestException): 입력값이 blank")
        void exceptionWhenNicknameIsBlank() {
            //given
            String nickname = "";

            //when
            Exception exception = catchException(() -> new UserNickname(nickname));

            //then
            assertThat(exception).isInstanceOf(BadRequestException.class);
        }

        @Test
        @DisplayName("예외(BadRequestException): 닉네임 10자 초과")
        void exceptionWhenNicknameLengthOver10() {
            //given
            String nickname = "test_nickname";

            //when
            Exception exception = catchException(() -> new UserNickname(nickname));

            //then
            assertThat(exception).isInstanceOf(BadRequestException.class);
        }
    }
}