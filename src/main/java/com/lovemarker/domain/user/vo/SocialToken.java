package com.lovemarker.domain.user.vo;

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
public class SocialToken {

    @Column(name = "social_token", nullable = false)
    private String socialToken;

    public SocialToken(String socialToken) {
        validateNotNull(socialToken);
        this.socialToken = socialToken;
    }

    private void validateNotNull(String socialToken) {
        if (Objects.isNull(socialToken) || socialToken.isBlank()) {
            throw new BadRequestException(ErrorCode.REQUEST_VALIDATION_EXCEPTION, "소셜 토큰은 필수 항목입니다.");
        }
    }
}
