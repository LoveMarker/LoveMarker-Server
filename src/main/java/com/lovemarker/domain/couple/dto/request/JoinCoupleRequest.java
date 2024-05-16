package com.lovemarker.domain.couple.dto.request;

import jakarta.validation.constraints.NotBlank;

public record JoinCoupleRequest(
    @NotBlank(message = "초대 코드는 필수 입력 항목입니다.") String invitationCode
) {

}
