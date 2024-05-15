package com.lovemarker.domain.couple.dto.response;

import com.lovemarker.domain.couple.Couple;

public record CreateInvitationCodeResponse(
    String invitationCode
) {
    public static CreateInvitationCodeResponse from(Couple couple) {
        return new CreateInvitationCodeResponse(couple.getInviteCode());
    }
}
