package com.lovemarker.domain.auth.dto.response;

public record ReissueTokenResponse(
    String accessToken
) {
    public static ReissueTokenResponse of(String accessToken) {
        return new ReissueTokenResponse(accessToken);
    }
}
