package com.lovemarker.domain.auth.service;

import com.lovemarker.domain.auth.dto.response.SocialInfoResponse;

public interface SocialSignInService {

    SocialInfoResponse getSocialInfo(String token);
}
