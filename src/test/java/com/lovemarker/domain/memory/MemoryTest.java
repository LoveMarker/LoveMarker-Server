package com.lovemarker.domain.memory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.catchException;

import com.lovemarker.domain.couple.Couple;
import com.lovemarker.domain.couple.fixture.CoupleFixture;
import com.lovemarker.domain.memory.fixture.AddressInfoFixture;
import com.lovemarker.domain.memory.vo.AddressInfo;
import com.lovemarker.domain.user.User;
import com.lovemarker.domain.user.fixture.UserFixture;
import com.lovemarker.global.exception.ForbiddenException;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class MemoryTest {

    @Nested
    @DisplayName("Memory 생성 시")
    class newMemoryTest {

        LocalDate date = LocalDate.now();
        String title = "test_title";
        String content = "test_content";
        AddressInfo addressInfo = AddressInfoFixture.addressInfo();
        Couple couple = CoupleFixture.couple();
        User user = UserFixture.user();
        List<String> urls = List.of("image1", "image2");

        @Test
        @DisplayName("성공")
        void newMemory() {
            //given
            user.connectCouple(couple);

            //when
            Memory memory = new Memory(date, title, content, addressInfo.getAddress(), addressInfo.getPosition(), user, urls);


            //then
            assertThat(memory.getTitle()).isEqualTo(title);
            assertThat(memory.getContent()).isEqualTo(content);
            assertThat(memory.getAddressInfo().getAddress()).isEqualTo(addressInfo.getAddress());
            assertThat(memory.getAddressInfo().getPosition()).isEqualTo(addressInfo.getPosition());
            assertThat(memory.getCouple()).isEqualTo(couple);
            assertThat(memory.getUser()).isEqualTo(user);
            assertThat(memory.getImages().size()).isEqualTo(urls.size());
        }

        @Test
        @DisplayName("예외(ForbiddenException): 커플 정보가 없을 때")
        void exceptionWhenCoupleIsNull() {
            //given
            //when
            Exception exception = catchException(() -> new Memory(date, title, content, addressInfo.getAddress(), addressInfo.getPosition(), user, urls));

            //then
            assertThat(exception).isInstanceOf(ForbiddenException.class);
        }
    }
}