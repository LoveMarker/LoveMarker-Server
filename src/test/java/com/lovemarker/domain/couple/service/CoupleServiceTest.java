package com.lovemarker.domain.couple.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.lovemarker.domain.couple.InviteCodeStrategy;
import com.lovemarker.domain.couple.repository.CoupleRepository;
import com.lovemarker.domain.user.User;
import com.lovemarker.domain.user.fixture.UserFixture;
import com.lovemarker.domain.user.repository.UserRepository;
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
            given(inviteCodeStrategy.generate()).willReturn("INVITE_CODE");

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
}