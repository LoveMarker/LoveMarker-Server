package com.lovemarker.domain.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import com.lovemarker.domain.user.User;
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
}