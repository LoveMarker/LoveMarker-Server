package com.lovemarker.domain.auth.dto.request;

import jakarta.validation.constraints.NotBlank;

public record SignInRequest(
    @NotBlank(message = "provider는 필수 입력 항목입니다.") String provider
) {

}
