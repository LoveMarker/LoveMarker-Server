package com.lovemarker.domain.user.dto.response;

import com.lovemarker.domain.user.User;

public record UpdateUserNicknameResponse(
    String nickname
) {
    public static UpdateUserNicknameResponse from(User user) {
        return new UpdateUserNicknameResponse(user.getNickname());
    }
}
