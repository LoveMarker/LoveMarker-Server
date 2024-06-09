package com.lovemarker.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateUserNicknameRequest(
    @NotBlank(message = "닉네임은 필수 입력 항목입니다.") String nickname
) {

}
