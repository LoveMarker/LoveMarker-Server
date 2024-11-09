package com.lovemarker.domain.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.catchException;

import com.lovemarker.domain.couple.Couple;
import com.lovemarker.domain.couple.fixture.CoupleFixture;
import com.lovemarker.domain.user.fixture.UserFixture;
import com.lovemarker.global.exception.BadRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class UserTest {

    @Nested
    @DisplayName("User 생성 시")
    class NewUserTest {

        String nickname = "nickname";
        String provider = "GOOGLE";
        String socialToken = "test_token";

        @Test
        @DisplayName("성공")
        void newUser() {
            //given
            //when
            User user = new User(nickname, provider, socialToken);

            //then
            assertThat(user.getNickname()).isEqualTo(nickname);
            assertThat(user.getProvider()).isEqualTo(provider);
            assertThat(user.getSocialToken()).isEqualTo(socialToken);
        }

        @Test
        @DisplayName("예외(IllegalArgumentException): 잘못된 socialType 인 경우")
        void exceptionWhenSocialTypeIsInvalid() {
            //given
            provider = "INVALID_PROVIDER";

            //when
            Exception exception = catchException(() -> new User(nickname, provider, socialToken));

            //then
            assertThat(exception).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("Couple 등록 시")
    class connectCoupleTest {

        User user = UserFixture.user();
        Couple couple = CoupleFixture.couple();

        @Test
        @DisplayName("성공")
        void makeCouple() {
            //given
            //when
            user.connectCouple(couple);

            //then
            assertThat(user.getCouple()).isEqualTo(couple);
        }

        @Test
        @DisplayName("예외(BadRequestException): 이미 커플 정보가 존재하는 경우")
        void exceptionWhenCoupleIsNotNull() {
            //given
            user.connectCouple(couple);

            //when
            Exception exception = catchException(() -> user.connectCouple(couple));

            //then
            assertThat(exception).isInstanceOf(BadRequestException.class);
        }
    }

    @Nested
    @DisplayName("커플 연결 해제 시")
    class DisconnectCoupleTest {

        User user = UserFixture.user();
        Couple couple = CoupleFixture.couple();

        @Test
        @DisplayName("성공")
        void disconnectCouple() {
            //given
            user.connectCouple(couple);

            //when
            user.disconnectCouple();

            //then
            assertThat(user.getCouple()).isNull();
        }
    }
}
