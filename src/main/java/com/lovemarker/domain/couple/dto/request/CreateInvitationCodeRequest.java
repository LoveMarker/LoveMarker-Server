package com.lovemarker.domain.couple.dto.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record CreateInvitationCodeRequest(
    @NotNull(message = "기념일은 필수 입력 항목입니다.") LocalDate anniversary
) {

}
