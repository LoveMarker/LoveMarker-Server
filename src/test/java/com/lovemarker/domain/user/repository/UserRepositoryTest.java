package com.lovemarker.domain.user.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.lovemarker.base.BaseRepositoryTest;
import com.lovemarker.domain.couple.Couple;
import com.lovemarker.domain.couple.fixture.CoupleFixture;
import com.lovemarker.domain.user.User;
import com.lovemarker.domain.user.fixture.UserFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class UserRepositoryTest extends BaseRepositoryTest {

    @Nested
    @DisplayName("countByCouple_CoupleId 메서드 실행 시")
    class CountByCouple_CoupleIdTest {

        @Test
        @DisplayName("성공")
        void countByCouple_CoupleId() {
            // given
            User user = UserFixture.user();
            Couple couple = CoupleFixture.couple();
            user.connectCouple(couple);
            userRepository.save(user);
            coupleRepository.save(couple);
            Long expected = 1L;

            //when
            Long result = userRepository.countByCouple_CoupleId(couple.getCoupleId());

            //then
            assertThat(result).isEqualTo(expected);
        }
    }

}