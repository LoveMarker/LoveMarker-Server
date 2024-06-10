package com.lovemarker.domain.memory.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.lovemarker.domain.couple.Couple;
import com.lovemarker.domain.couple.fixture.CoupleFixture;
import com.lovemarker.domain.memory.repository.MemoryRepository;
import com.lovemarker.domain.user.User;
import com.lovemarker.domain.user.exception.UserNotFoundException;
import com.lovemarker.domain.user.fixture.UserFixture;
import com.lovemarker.domain.user.repository.UserRepository;
import com.lovemarker.global.exception.ForbiddenException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.GeometryFactory;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MemoryServiceTest {

    @InjectMocks
    MemoryService memoryService;

    @Mock
    MemoryRepository memoryRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    GeometryFactory geometryFactory;

    @Nested
    @DisplayName("createMemory 메서드 실행 시")
    class CreateMemoryTest {

        User user = UserFixture.user();
        User partner = new User("partner", "GOOGLE", "token");
        Couple couple = CoupleFixture.couple();
        LocalDate date = LocalDate.now();
        String title = "title";
        String content = "content";
        Double latitude = 3.2;
        Double longitude = 4.3;
        String address = "address";
        List<String> images = List.of("url1", "url2");

        @Test
        @DisplayName("성공")
        void createMemory() {
            //given
            user.connectCouple(couple);
            given(userRepository.findById(anyLong())).willReturn(Optional.ofNullable(user));
            given(userRepository.countByCouple_CoupleId(any())).willReturn(2L);

            //when
            memoryService.createMemory(1L, date, title, content, latitude, longitude, address, images);

            //then
            then(memoryRepository).should().save(any());
        }

        @Test
        @DisplayName("예외(UserNotFoundException): 존재하지 않는 유저")
        void exceptionWhenUserNotFound() {
            //given
            //when
            Exception exception = catchException(() -> memoryService.createMemory(1L, date, title, content, latitude, longitude, address, images));

            //then
            assertThat(exception).isInstanceOf(UserNotFoundException.class);
        }

        @Test
        @DisplayName("예외(ForbiddenException): 커플 정보가 없는 경우")
        void exceptionWhenNoCouple() {
            //given
            given(userRepository.findById(anyLong())).willReturn(Optional.ofNullable(user));

            //when
            Exception exception = catchException(() -> memoryService.createMemory(1L, date, title, content, latitude, longitude, address, images));

            //then
            assertThat(exception).isInstanceOf(ForbiddenException.class);
        }

        @Test
        @DisplayName("예외(ForbiddenException): 커플 연결이 되지 않은 경우")
        void exceptionWhenNotCoupleConnected() {
            //given
            user.connectCouple(couple);
            given(userRepository.findById(anyLong())).willReturn(Optional.ofNullable(user));
            given(userRepository.countByCouple_CoupleId(any())).willReturn(1L);

            //when
            Exception exception = catchException(() -> memoryService.createMemory(1L, date, title, content, latitude, longitude, address, images));

            //then
            assertThat(exception).isInstanceOf(ForbiddenException.class);
        }
    }
}