package com.lovemarker.domain.auth.dto.response;

public record SocialInfoResponse(
    String email,
    String socialId
) {
    public static SocialInfoResponse of(String email, String socialId) {
        return new SocialInfoResponse(email, socialId);
    }
}
