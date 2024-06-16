package com.lovemarker.domain.memory.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

import com.lovemarker.domain.couple.Couple;
import com.lovemarker.domain.couple.fixture.CoupleFixture;
import com.lovemarker.domain.memory.Memory;
import com.lovemarker.domain.memory.dto.response.FindMemoryByRadiusResponse;
import com.lovemarker.domain.memory.dto.response.FindMemoryDetail;
import com.lovemarker.domain.memory.dto.response.FindMemoryListResponse;
import com.lovemarker.domain.memory.exception.MemoryNotFoundException;
import com.lovemarker.domain.memory.fixture.MemoryFixture;
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
import org.locationtech.jts.geom.Point;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

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
    }

    @Nested
    @DisplayName("findMemoryDetail 메서드 실행 시")
    class FindMemoryDetailTest {

        User user = UserFixture.user();
        Couple couple = CoupleFixture.couple();

        @Test
        @DisplayName("성공")
        void findMemoryDetail() {
            //given
            user.connectCouple(couple);
            Memory memory = MemoryFixture.memory(user);
            given(memoryRepository.findById(anyLong())).willReturn(Optional.ofNullable(memory));

            //when
            FindMemoryDetail result = memoryService.findMemoryDetail(1L, 1L);

            //then
            assertThat(result.date()).isEqualTo(memory.getDate());
            assertThat(result.title()).isEqualTo(memory.getTitle());
        }

        @Test
        @DisplayName("예외(MemoryNotFoundException): 존재하지 않는 추억")
        void exceptionWhenNotFoundMemory() {
            //given
            //when
            Exception exception = catchException(() -> memoryService.findMemoryDetail(1L, 1L));

            //then
            assertThat(exception).isInstanceOf(MemoryNotFoundException.class);

        }
    }

    @Nested
    @DisplayName("findMemoryList 메서드 실행 시")
    class FindMemoryListTest {

        User user = UserFixture.user();
        Couple couple = CoupleFixture.couple();
        Memory memory;
        List<Memory> memoryList;

        @Test
        @DisplayName("성공")
        void findMemoryList() {
            //given
            user.connectCouple(couple);
            memory = MemoryFixture.memory(user);
            memoryList = List.of(memory);
            Page<Memory> page = new PageImpl<>(memoryList);
            given(userRepository.findById(any())).willReturn(Optional.ofNullable(user));
            given(memoryRepository.findByCouple_CoupleIdOrderByCreatedAtDesc(any(), any()))
                .willReturn(page);

            //when
            FindMemoryListResponse result = memoryService.findMemoryList(1L, 0, 10);

            //then
            assertThat(result.pageInfo().totalElements()).isEqualTo(1);
        }

        @Test
        @DisplayName("예외(UserNotFoundException): 존재하지 않는 유저")
        void exceptionWhenNotFoundUser() {
            //given
            //when
            Exception exception = catchException(() -> memoryService.findMemoryList(1L, 0, 10));

            //then
            assertThat(exception).isInstanceOf(UserNotFoundException.class);
        }
    }

    @Nested
    @DisplayName("findMyMemoryList 메서드 실행 시")
    class FindMyMemoryListTest {

        User user = UserFixture.user();
        Couple couple = CoupleFixture.couple();
        Memory memory;
        List<Memory> memoryList;

        @Test
        @DisplayName("성공")
        void findMemoryList() {
            //given
            user.connectCouple(couple);
            memory = MemoryFixture.memory(user);
            memoryList = List.of(memory);
            Page<Memory> page = new PageImpl<>(memoryList);
            given(userRepository.findById(any())).willReturn(Optional.ofNullable(user));
            given(memoryRepository.findByUser_UserIdOrderByCreatedAtDesc(any(), any()))
                .willReturn(page);

            //when
            FindMemoryListResponse result = memoryService.findMyMemoryList(1L, 0, 10);

            //then
            assertThat(result.pageInfo().totalElements()).isEqualTo(1);
        }

        @Test
        @DisplayName("예외(UserNotFoundException): 존재하지 않는 유저")
        void exceptionWhenNotFoundUser() {
            //given
            //when
            Exception exception = catchException(() -> memoryService.findMyMemoryList(1L, 0, 10));

            //then
            assertThat(exception).isInstanceOf(UserNotFoundException.class);
        }
    }

    @Nested
    @DisplayName("findMemoryByRadius 메서드 실행 시")
    class FindMemoryByRadiusTest {

        User user = UserFixture.user();
        Couple couple = CoupleFixture.couple();
        Memory memory;
        List<Memory> memoryList;

        @Test
        @DisplayName("성공")
        void findMemoryByRadius() {
            //given
            user.connectCouple(couple);

            Point mockPoint = mock(Point.class);
            given(mockPoint.getX()).willReturn(3.2);
            given(mockPoint.getY()).willReturn(4.4);

            memory = new Memory(LocalDate.now(), "title", "content", "address", mockPoint, user, List.of("url"));

            memoryList = List.of(memory);
            FindMemoryByRadiusResponse.FindMemoryResponse findMemoryResponse = new FindMemoryByRadiusResponse.FindMemoryResponse(
                1L, 3.2, 4.4);
            FindMemoryByRadiusResponse expected = new FindMemoryByRadiusResponse(List.of(findMemoryResponse));

            given(userRepository.findById(any())).willReturn(Optional.ofNullable(user));
            given(memoryRepository.findByRadius(any(), any(), anyDouble())).willReturn(memoryList);

            //when
            FindMemoryByRadiusResponse result = memoryService.findMemoryByRadius(1L, 30.4, 3.2, 4.4);

            //then
            assertThat(result.memories().size()).isEqualTo(expected.memories().size());
        }

        @Test
        @DisplayName("예외(UserNotFoundException): 존재하지 않는 유저")
        void exceptionWhenNotFoundUser() {
            //given
            //when
            Exception exception = catchException(() -> memoryService.findMemoryByRadius(1L, 30.4, 3.2, 4.4));

            //then
            assertThat(exception).isInstanceOf(UserNotFoundException.class);
        }
    }
}