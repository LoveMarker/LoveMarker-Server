package com.lovemarker.domain.user.dto;

public record FindMyPageResponse(
    boolean isMatched,
    String nickname,
    Long anniversaryDays,
    String partnerNickname
) {
    public static FindMyPageResponse of(boolean isMatched, String nickname) {
        return new FindMyPageResponse(isMatched, nickname, null, null);
    }

    public static FindMyPageResponse of(boolean isMatched, String nickname, Long anniversaryDays) {
        return new FindMyPageResponse(isMatched, nickname, anniversaryDays, null);
    }

    public static FindMyPageResponse of(boolean isMatched, String nickname, Long anniversaryDays, String partnerNickname) {
        return new FindMyPageResponse(isMatched, nickname, anniversaryDays, partnerNickname);
    }
}
