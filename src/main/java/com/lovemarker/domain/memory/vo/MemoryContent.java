package com.lovemarker.domain.memory.vo;

import com.lovemarker.global.constant.ErrorCode;
import com.lovemarker.global.exception.BadRequestException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemoryContent {

    private static final int MAX_CONTENT_LENGTH = 100;

    @Column(name = "content", nullable = false)
    private String content;

    public MemoryContent(String content) {
        validateNotNull(content);
        validateTitleLength(content);
        this.content = content;
    }

    private void validateNotNull(String content) {
        if (Objects.isNull(content) || content.isBlank()) {
            throw new BadRequestException(ErrorCode.REQUEST_VALIDATION_EXCEPTION, "내용은 필수 항목입니다.");
        }
    }

    private void validateTitleLength(String content) {
        if (content.length() > MAX_CONTENT_LENGTH) {
            throw new BadRequestException(ErrorCode.REQUEST_VALIDATION_EXCEPTION, "내용은 최대 100자입니다.");
        }
    }
}
