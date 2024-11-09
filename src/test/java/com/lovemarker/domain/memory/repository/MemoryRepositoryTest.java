package com.lovemarker.domain.memory.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.lovemarker.base.BaseRepositoryTest;
import com.lovemarker.domain.couple.Couple;
import com.lovemarker.domain.couple.fixture.CoupleFixture;
import com.lovemarker.domain.memory.Memory;
import com.lovemarker.domain.memory.fixture.MemoryFixture;
import com.lovemarker.domain.user.User;
import com.lovemarker.domain.user.fixture.UserFixture;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

class MemoryRepositoryTest extends BaseRepositoryTest {

    @Nested
    @DisplayName("findByCouple_CoupleIdOrderByCreatedAtDesc 메서드 실행 시")
    class FindByCouple_CoupleIdOrderByCreatedAtDescTest {

        @Test
        @DisplayName("성공")
        void findByCouple_CoupleIdOrderByCreatedAtDesc() {
            //given
            User user = UserFixture.user();
            Couple couple = CoupleFixture.couple();
            user.connectCouple(couple);
            Memory memory = MemoryFixture.memory(user);

            userRepository.save(user);
            coupleRepository.save(couple);
            memoryRepository.save(memory);

            PageRequest pageRequest = PageRequest.of(0, 10);
            List<Memory> memoryList = List.of(memory);
            Page<Memory> expected = new PageImpl<>(memoryList);

            //when
            Page<Memory> result = memoryRepository
                .findByCouple_CoupleIdOrderByCreatedAtDesc(couple.getCoupleId(), pageRequest);

            //then
            assertThat(result.getTotalElements()).isEqualTo(expected.getTotalElements());
        }
    }

    @Nested
    @DisplayName("findByUser_UserIdOrderByCreatedAtDesc 메서드 실행 시")
    class FindByUser_UserIdOrderByCreatedAtDescTest {

        User user = UserFixture.user();
        Couple couple = CoupleFixture.couple();
        Memory memory;

        @Test
        @DisplayName("성공")
        void findByUser_UserIdOrderByCreatedAtDesc() {
            //given
            user.connectCouple(couple);
            memory = MemoryFixture.memory(user);

            userRepository.save(user);
            coupleRepository.save(couple);
            memoryRepository.save(memory);

            PageRequest pageRequest = PageRequest.of(0, 10);
            List<Memory> memoryList = List.of(memory);
            Page<Memory> expected = new PageImpl<>(memoryList);

            //when
            Page<Memory> result = memoryRepository
                .findByUser_UserIdOrderByCreatedAtDesc(user.getUserId(), pageRequest);

            //then
            assertThat(result.getTotalElements()).isEqualTo(expected.getTotalElements());
        }
    }

}