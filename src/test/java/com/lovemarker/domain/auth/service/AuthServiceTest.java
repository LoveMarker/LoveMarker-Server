package com.lovemarker.domain.auth.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.catchException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import com.lovemarker.domain.auth.RandomNicknameGenerator;
import com.lovemarker.domain.auth.dto.response.ReissueTokenResponse;
import com.lovemarker.domain.auth.dto.response.SignInResponse;
import com.lovemarker.domain.auth.dto.response.SocialInfoResponse;
import com.lovemarker.domain.auth.exception.InvalidTokenException;
import com.lovemarker.domain.auth.exception.TimeExpiredTokenException;
import com.lovemarker.domain.user.User;
import com.lovemarker.domain.user.exception.UserNotFoundException;
import com.lovemarker.domain.user.fixture.UserFixture;
import com.lovemarker.domain.user.repository.UserRepository;
import com.lovemarker.global.constant.TokenStatus;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    AuthService authService;

    @Mock
    UserRepository userRepository;

    @Mock
    SocialSignInService socialSignInService;

    @Mock
    JwtService jwtService;

    @Mock
    RandomNicknameGenerator randomNicknameGenerator;

    @Mock
    TokenCacheService tokenCacheService;

    @Nested
    @DisplayName("signIn 메서드 실행 시")
    class SignInTest {

        User user = UserFixture.user();
        SocialInfoResponse socialInfo = new SocialInfoResponse("email", "social_id");
        String social_token = "social_token";
        String provider = "GOOGLE";

        @Test
        @DisplayName("성공: 로그인")
        void signIn_login() {
            //given
            given(socialSignInService.getSocialInfo(any())).willReturn(socialInfo);
            given(userRepository.existsBySocialToken_SocialTokenAndProvider(any(), any())).willReturn(true);
            given(userRepository.findBySocialToken_SocialTokenAndProvider(any(), any())).willReturn(
                Optional.ofNullable(user));

            //when
            SignInResponse response = authService.signIn(social_token, provider);

            //then
            assertThat(response.type()).isEqualTo("Login");
        }

        @Test
        @DisplayName("성공: 회원가입")
        void signIn_signup() {
            //given
            given(socialSignInService.getSocialInfo(any())).willReturn(socialInfo);
            given(userRepository.existsBySocialToken_SocialTokenAndProvider(any(), any())).willReturn(false);
            given(randomNicknameGenerator.generate()).willReturn("닉네임");
            given(userRepository.findBySocialToken_SocialTokenAndProvider(any(), any())).willReturn(
                Optional.ofNullable(user));

            //when
            SignInResponse response = authService.signIn(social_token, provider);

            //then
            assertThat(response.type()).isEqualTo("Signup");
        }

        @Test
        @DisplayName("예외(UserNotFoundException): 존재하지 않는 유저")
        void exceptionWhenUserNotFound() {
            //given
            given(socialSignInService.getSocialInfo(any())).willReturn(socialInfo);
            given(userRepository.existsBySocialToken_SocialTokenAndProvider(any(), any())).willReturn(true);

            //when
            Exception exception = catchException(() -> authService.signIn(social_token, provider));

            //then
            assertThat(exception).isInstanceOf(UserNotFoundException.class);
        }
    }

    @Nested
    @DisplayName("reissueToken 메서드 실행 시")
    class ReissueTokenTest {

        String refreshToken = "validRefreshToken";
        String newAccessToken = "newAccessToken";
        User user = UserFixture.user();

        @Test
        @DisplayName("성공")
        void reissueToken() {
            //given
            given(jwtService.verifyToken(any())).willReturn(TokenStatus.TOKEN_VALID);
            given(jwtService.getJwtContents(any())).willReturn("1");
            given(tokenCacheService.getValuesByKey(any())).willReturn("token");
            given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
            given(jwtService.issuedAccessToken(any())).willReturn(newAccessToken);

            //when
            ReissueTokenResponse response = authService.reissueToken(refreshToken);

            //then
            assertThat(response).isNotNull();
            assertThat(response.accessToken()).isEqualTo(newAccessToken);
        }

        @Test
        @DisplayName("예외(InvalidTokenException): refresh token verify 실패")
        void exceptionWhenInvalidToken() {
            //given
            given(jwtService.verifyToken(any())).willReturn(TokenStatus.TOKEN_INVALID);

            //when
            Exception exception = catchException(() -> authService.reissueToken(any()));

            //then
            assertThat(exception).isInstanceOf(InvalidTokenException.class);
        }

        @Test
        @DisplayName("예외(TimeExpiredTokenException): refresh token time expired")
        void exceptionWhenExpiredToken() {
            //given
            given(jwtService.verifyToken(any())).willReturn(TokenStatus.TOKEN_EXPIRED);

            //when
            Exception exception = catchException(() -> authService.reissueToken(any()));

            //then
            assertThat(exception).isInstanceOf(TimeExpiredTokenException.class);
        }

        @Test
        @DisplayName("예외(UserNotFoundException): 존재하지 않는 유저")
        void exceptionWhenUserNotFound() {
            //given
            given(jwtService.verifyToken(any())).willReturn(TokenStatus.TOKEN_VALID);
            given(jwtService.getJwtContents(any())).willReturn("1");
            given(tokenCacheService.getValuesByKey(any())).willReturn("token");

            //when
            Exception exception = catchException(() -> authService.reissueToken(any()));

            //then
            assertThat(exception).isInstanceOf(UserNotFoundException.class);
        }
    }
}
