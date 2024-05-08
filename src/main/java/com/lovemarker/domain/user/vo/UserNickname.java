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
public class UserNickname {

    private static final int MAX_NICKNAME_LENGTH = 10;

    @Column(name = "nickname")
    private String nickname;

    public UserNickname(String nickname) {
        validateNotNull(nickname);
        validateNicknameLength(nickname);
        this.nickname = nickname;
    }

    private void validateNotNull(String nickname) {
        if (Objects.isNull(nickname) || nickname.isBlank()) {
            throw new BadRequestException(ErrorCode.REQUEST_VALIDATION_EXCEPTION, "닉네임은 필수 항목입니다.");
        }
    }

    private void validateNicknameLength(String nickname) {
        if (nickname.length() > MAX_NICKNAME_LENGTH) {
            throw new BadRequestException(ErrorCode.REQUEST_VALIDATION_EXCEPTION, "닉네임은 최대 10자입니다.");
        }
    }
}
