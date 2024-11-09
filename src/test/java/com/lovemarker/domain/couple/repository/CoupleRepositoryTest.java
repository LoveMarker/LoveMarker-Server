package com.lovemarker.domain.couple.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.lovemarker.base.BaseRepositoryTest;
import com.lovemarker.domain.couple.Couple;
import com.lovemarker.domain.couple.fixture.CoupleFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CoupleRepositoryTest extends BaseRepositoryTest {

    @Nested
    @DisplayName("findByInviteCode 메서드 실행 시")
    class FindByInviteCodeTest {

        @Test
        @DisplayName("성공")
        void findByInviteCode() {
            //given
            Couple couple = CoupleFixture.couple();
            coupleRepository.save(couple);
            String expected = couple.getInviteCode();

            //when
            String result = coupleRepository.findByInviteCode(expected).get().getInviteCode();

            //then
            assertThat(result).isEqualTo(expected);
        }
    }
}