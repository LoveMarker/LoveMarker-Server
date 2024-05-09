package com.lovemarker.domain.memory.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

import com.lovemarker.global.exception.BadRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class MemoryTitleTest {

    @Nested
    @DisplayName("MemoryTitle 생성 시")
    class NewMemoryTitleTest {

        @Test
        @DisplayName("성공")
        void newMemoryTitle() {
            //given
            String title = "test_title";

            //when
            MemoryTitle memoryTitle = new MemoryTitle(title);

            //then
            assertThat(memoryTitle.getTitle()).isEqualTo(title);
        }

        @Test
        @DisplayName("예외(BadRequestException): 입력값이 null")
        void exceptionWhenTitleIsNull() {
            //given
            String title = null;

            //when
            Exception exception = catchException(() -> new MemoryTitle(title));

            //then
            assertThat(exception).isInstanceOf(BadRequestException.class);
        }

        @Test
        @DisplayName("예외(BadRequestException): 입력값이 blank")
        void exceptionWhenTitleIsBlank() {
            //given
            String title = "";

            //when
            Exception exception = catchException(() -> new MemoryTitle(title));

            //then
            assertThat(exception).isInstanceOf(BadRequestException.class);
        }

        @Test
        @DisplayName("예외(BadRequestException): 입력값이 30자 초과")
        void exceptionWhenTitleIsOver30() {
            //given
            String title = "a".repeat(31);

            //when
            Exception exception = catchException(() -> new MemoryTitle(title));

            //then
            assertThat(exception).isInstanceOf(BadRequestException.class);
        }
    }
}