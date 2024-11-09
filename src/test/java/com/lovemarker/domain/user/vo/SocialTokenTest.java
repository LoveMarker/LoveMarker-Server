package com.lovemarker.domain.user.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

import com.lovemarker.global.exception.BadRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class SocialTokenTest {

    @Nested
    @DisplayName("SocialToken 생성 시")
    class NewSocialTokenTest {

        @Test
        @DisplayName("성공")
        void newSocialToken() {
            //given
            String token = "test_token";

            //when
            SocialToken socialToken = new SocialToken(token);

            //then
            assertThat(socialToken.getSocialToken()).isEqualTo(token);
        }

        @Test
        @DisplayName("예외(BadRequestException): 입력값이 null")
        void exceptionWhenSocialTokenIsNull() {
            //given
            String token = null;

            //when
            Exception exception = catchException(() -> new SocialToken(token));

            //then
            assertThat(exception).isInstanceOf(BadRequestException.class);
        }

        @Test
        @DisplayName("예외(BadRequestException): 입력값이 blank")
        void exceptionWhenSocialTokenIsBlank() {
            //given
            String token = "";

            //when
            Exception exception = catchException(() -> new SocialToken(token));

            //then
            assertThat(exception).isInstanceOf(BadRequestException.class);
        }
    }
}