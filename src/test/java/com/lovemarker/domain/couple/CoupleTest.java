package com.lovemarker.domain.couple;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CoupleTest {

    @Nested
    @DisplayName("Couple 생성 시")
    class NewCoupleTest {

        LocalDate anniversary = LocalDate.now();
        String inviteCode = "inviteCode";

        @Test
        @DisplayName("성공")
        void newCouple() {
            //given
            //when
            Couple couple = new Couple(anniversary, inviteCode);

            //then
            assertThat(couple.getAnniversary()).isEqualTo(anniversary);
            assertThat(couple.getInviteCode()).isEqualTo(inviteCode);
        }
    }
}