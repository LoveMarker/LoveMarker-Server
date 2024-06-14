package com.lovemarker.domain.couple.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.lovemarker.domain.couple.Couple;
import com.lovemarker.domain.couple.InviteCodeStrategy;
import com.lovemarker.domain.couple.fixture.CoupleFixture;
import com.lovemarker.domain.couple.repository.CoupleRepository;
import com.lovemarker.domain.user.User;
import com.lovemarker.domain.user.exception.UserNotFoundException;
import com.lovemarker.domain.user.fixture.UserFixture;
import com.lovemarker.domain.user.repository.UserRepository;
import com.lovemarker.global.exception.BadRequestException;
import com.lovemarker.global.exception.NotFoundException;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CoupleServiceTest {

    @InjectMocks
    CoupleService coupleService;

    @Mock
    CoupleRepository coupleRepository;

    @Mock
    InviteCodeStrategy inviteCodeStrategy;

    @Mock
    UserRepository userRepository;

    @Nested
    @DisplayName("createInvitationCode 메서드 실행 시")
    class CreateInvitationCodeTest {

        User user = UserFixture.user();
        LocalDate anniversary = LocalDate.parse("2023-02-15");

        @Test
        @DisplayName("성공")
        void createInvitationCode() {
            //given
            given(userRepository.findById(anyLong())).willReturn(Optional.ofNullable(user));

            //when
            coupleService.createInvitationCode(1L, anniversary);

            //then
            then(coupleRepository).should().save(any());
        }

        @Test
        @DisplayName("예외(NotFoundException): 존재하지 않는 유저")
        void exceptionWhenUserNotFound() {
            //given
            //when
            Exception exception = catchException(() -> coupleService.createInvitationCode(1L, anniversary));

            //then
            assertThat(exception).isInstanceOf(NotFoundException.class);
        }
    }

    @Nested
    @DisplayName("joinCouple 메서드 실행 시")
    class JoinCoupleTest {

        User user = UserFixture.user();
        Couple couple = CoupleFixture.couple();

        @Test
        @DisplayName("성공")
        void joinCouple() {
            //given
            given(userRepository.findById(anyLong())).willReturn(Optional.ofNullable(user));
            given(coupleRepository.findByInviteCode(any())).willReturn(Optional.ofNullable(couple));
            given(userRepository.countByCouple_CoupleId(any())).willReturn(1L);

            //when
            coupleService.joinCouple(1L, "inviteCode");

            //then
            assertThat(user.getCouple()).isEqualTo(couple);
        }

        @Test
        @DisplayName("예외(NotFoundException): 존재하지 않는 유저")
        void exceptionWhenUserNotFound() {
            //given
            //when
            Exception exception = catchException(() -> coupleService.joinCouple(1L, "inviteCode"));

            //then
            assertThat(exception).isInstanceOf(NotFoundException.class);
        }

        @Test
        @DisplayName("예외(NotFoundException): 존재하지 않는 커플")
        void exceptionWhenCoupleNotFound() {
            //given
            given(userRepository.findById(anyLong())).willReturn(Optional.ofNullable(user));

            //when
            Exception exception = catchException(() -> coupleService.joinCouple(1L, "inviteCode"));

            //then
            assertThat(exception).isInstanceOf(NotFoundException.class);
        }

        @Test
        @DisplayName("예외(BadRequestException): 이미 연결이 완료된 커플")
        void exceptionWhenCoupleAlreadyConnected() {
            //given
            given(userRepository.findById(anyLong())).willReturn(Optional.ofNullable(user));
            given(coupleRepository.findByInviteCode(any())).willReturn(Optional.ofNullable(couple));
            given(userRepository.countByCouple_CoupleId(any())).willReturn(2L);

            //when
            Exception exception = catchException(() -> coupleService.joinCouple(1L, "inviteCode"));

            //then
            assertThat(exception).isInstanceOf(BadRequestException.class);
        }
    }

    @Nested
    @DisplayName("disconnectCouple 메서드 실행 시")
    class DisconnectCoupleTest {

        User user = UserFixture.user();
        Couple couple = CoupleFixture.couple();

        @Test
        @DisplayName("성공")
        void disconnectCouple() {
            //given
            user.connectCouple(couple);

            given(userRepository.findById(anyLong())).willReturn(Optional.ofNullable(user));

            //when
            coupleService.disconnectCouple(1L);

            //then
            assertThat(user.getCouple()).isEqualTo(null);
        }

        @Test
        @DisplayName("예외(UserNotFoundException): 존재하지 않는 유저")
        void exceptionWhenUserNotFound() {
            //given
            //when
            Exception exception = catchException(() -> coupleService.disconnectCouple(1L));

            //then
            assertThat(exception).isInstanceOf(UserNotFoundException.class);
        }
    }
}