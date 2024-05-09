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
public class MemoryTitle {

    private static final int MAX_TITLE_LENGTH = 30;

    @Column(name = "title", nullable = false)
    private String title;

    public MemoryTitle(String title) {
        validateNotNull(title);
        validateTitleLength(title);
        this.title = title;
    }

    private void validateNotNull(String title) {
        if (Objects.isNull(title) || title.isBlank()) {
            throw new BadRequestException(ErrorCode.REQUEST_VALIDATION_EXCEPTION, "제목은 필수 항목입니다.");
        }
    }

    private void validateTitleLength(String title) {
        if (title.length() > MAX_TITLE_LENGTH) {
            throw new BadRequestException(ErrorCode.REQUEST_VALIDATION_EXCEPTION, "제목은 최대 30자입니다.");
        }
    }
}
