package com.lovemarker.domain.memory.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.lovemarker.global.exception.BadRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class MemoryContentTest {

    @Nested
    @DisplayName("MemoryContent 생성 시")
    class NewMemoryContentTest {

        @Test
        @DisplayName("성공")
        void newMemoryContent() {
            //given
            String content = "test_content";

            //when
            MemoryContent memoryContent = new MemoryContent(content);

            //then
            assertEquals(memoryContent.getContent(), content);
        }

        @Test
        @DisplayName("예외(BadRequestException): 입력값이 null")
        void exceptionWhenContentIsNull() {
            //given
            String content = null;

            //when
            Exception exception = catchException(() -> new MemoryContent(content));

            //then
            assertThat(exception).isInstanceOf(BadRequestException.class);
        }

        @Test
        @DisplayName("예외(BadRequestException): 입력값이 blank")
        void exceptionWhenContentIsBlank() {
            //given
            String content = "";

            //when
            Exception exception = catchException(() -> new MemoryContent(content));

            //then
            assertThat(exception).isInstanceOf(BadRequestException.class);
        }

        @Test
        @DisplayName("예외(BadRequestException): 입력값이 100자 초과")
        void exceptionWhenContentIsOver100() {
            //given
            String content = "a".repeat(101);

            //when
            Exception exception = catchException(() -> new MemoryContent(content));

            //then
            assertThat(exception).isInstanceOf(BadRequestException.class);
        }
    }
}