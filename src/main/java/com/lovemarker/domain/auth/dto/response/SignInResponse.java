package com.lovemarker.domain.auth.dto.response;

public record SignInResponse(
    String type,
    String accessToken,
    String refreshToken
) {
    public static SignInResponse of(String type, String accessToken, String refreshToken) {
        return new SignInResponse(type, accessToken, refreshToken);
    }
}
