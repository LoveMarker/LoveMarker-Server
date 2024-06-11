package com.lovemarker.domain.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import com.lovemarker.domain.couple.Couple;
import com.lovemarker.domain.couple.fixture.CoupleFixture;
import com.lovemarker.domain.user.User;
import com.lovemarker.domain.user.dto.response.FindMyPageResponse;
import com.lovemarker.domain.user.exception.UserNicknameDuplicateException;
import com.lovemarker.domain.user.exception.UserNotFoundException;
import com.lovemarker.domain.user.fixture.UserFixture;
import com.lovemarker.domain.user.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Nested
    @DisplayName("updateUserNickname 메서드 실행 시")
    class UpdateUserNicknameTest {

        User user = UserFixture.user();
        String nickname = "new";

        @Test
        @DisplayName("성공")
        void updateUserNickname() {
            //given
            given(userRepository.existsByNickname_Nickname(any())).willReturn(false);
            given(userRepository.findById(anyLong())).willReturn(Optional.ofNullable(user));

            //when
            userService.updateUserNickname(1L, nickname);

            //then
            assertThat(user.getNickname()).isEqualTo(nickname);
        }

        @Test
        @DisplayName("예외(UserNicknameDuplicateException): 중복된 닉네임")
        void exceptionWhenNicknameIsDuplicated() {
            //given
            given(userRepository.existsByNickname_Nickname(any())).willReturn(true);

            //when
            Exception exception = catchException(() -> userService.updateUserNickname(1L, nickname));

            //then
            assertThat(exception).isInstanceOf(UserNicknameDuplicateException.class);
        }

        @Test
        @DisplayName("예외(UserNotFoundException): 존재하지 않는 유저")
        void exceptionWhenUserIsNull() {
            //given
            given(userRepository.existsByNickname_Nickname(any())).willReturn(false);

            //when
            Exception exception = catchException(() -> userService.updateUserNickname(1L, nickname));

            //then
            assertThat(exception).isInstanceOf(UserNotFoundException.class);
        }
    }

    @Nested
    @DisplayName("findMyPage 메서드 실행 시")
    class FindMyPageTest {

        User user = UserFixture.user();
        User partner = new User("partner", "GOOGLE", "id");
        Couple couple = CoupleFixture.couple();

        @Test
        @DisplayName("성공: 커플 생성 O, 상대방 수락 O")
        void findMyPage() {
            //given
            user.connectCouple(couple);
            partner.connectCouple(couple);

            given(userRepository.findById(anyLong())).willReturn(Optional.ofNullable(user));
            given(userRepository.countByCouple_CoupleId(any())).willReturn(2L);
            given(userRepository.findPartnerUser(any(), any())).willReturn(Optional.ofNullable(partner));

            //when
            FindMyPageResponse result = userService.findMyPage(1L);

            //then
            assertThat(result.nickname()).isEqualTo(user.getNickname());
            assertThat(result.partnerNickname()).isEqualTo(partner.getNickname());
        }

        @Test
        @DisplayName("성공: 커플 생성 O, 상대방 수락 X")
        void findMyPageWithCouple() {
            //given
            user.connectCouple(couple);

            given(userRepository.findById(anyLong())).willReturn(Optional.ofNullable(user));
            given(userRepository.countByCouple_CoupleId(any())).willReturn(1L);

            //when
            FindMyPageResponse result = userService.findMyPage(1L);

            //then
            assertThat(result.nickname()).isEqualTo(user.getNickname());
            assertThat(result.partnerNickname()).isNull();
        }

        @Test
        @DisplayName("성공: 커플 생성 OX 상대방 수락 X")
        void findMyPageWithoutCouple() {
            //given
            given(userRepository.findById(anyLong())).willReturn(Optional.ofNullable(user));

            //when
            FindMyPageResponse result = userService.findMyPage(1L);

            //then
            assertThat(result.nickname()).isEqualTo(user.getNickname());
            assertThat(result.anniversaryDays()).isNull();
            assertThat(result.partnerNickname()).isNull();
        }

        @Test
        @DisplayName("예외(UserNotFoundException): 존재하지 않는 유저")
        void exceptionWhenUserIsNull() {
            //given
            //when
            Exception exception = catchException(() -> userService.findMyPage(1L));

            //then
            assertThat(exception).isInstanceOf(UserNotFoundException.class);
        }

        @Test
        @DisplayName("예외(UserNotFoundException): 존재하지 않는 파트너 유저")
        void exceptionWhenPartnerUserIsNull() {
            //given
            user.connectCouple(couple);
            partner.connectCouple(couple);

            given(userRepository.findById(anyLong())).willReturn(Optional.ofNullable(user));
            given(userRepository.countByCouple_CoupleId(any())).willReturn(2L);

            //when
            Exception exception = catchException(() -> userService.findMyPage(1L));

            //then
            assertThat(exception).isInstanceOf(UserNotFoundException.class);
        }
    }
}