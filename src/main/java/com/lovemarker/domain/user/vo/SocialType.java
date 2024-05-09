package com.lovemarker.domain.user.vo;

import com.lovemarker.global.constant.ErrorCode;
import com.lovemarker.global.exception.NotFoundException;
import java.util.Arrays;

public enum SocialType {
    GOOGLE;

    public static SocialType from(String type) {
        return Arrays.stream(SocialType.values())
            .filter(socialType -> socialType.name().equalsIgnoreCase(type))
            .findAny()
            .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_EXCEPTION, "존재하지 않는 provider 입니다."));
    }

}
