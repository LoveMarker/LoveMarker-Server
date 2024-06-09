package com.lovemarker.domain.auth.service;

import com.lovemarker.domain.auth.RandomNicknameGenerator;
import com.lovemarker.domain.auth.dto.response.SignInResponse;
import com.lovemarker.domain.auth.dto.response.SocialInfoResponse;
import com.lovemarker.domain.user.User;
import com.lovemarker.domain.user.exception.UserNotFoundException;
import com.lovemarker.domain.user.repository.UserRepository;
import com.lovemarker.domain.user.vo.SocialType;
import com.lovemarker.global.constant.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final SocialSignInService socialSignInService;
    private final JwtService jwtService;
    private final RandomNicknameGenerator randomNicknameGenerator;

    @Transactional
    public SignInResponse signIn(String token, String type) {
        SocialType socialType = SocialType.valueOf(type);
        SocialInfoResponse socialInfo = socialSignInService.getSocialInfo(token);

        boolean isRegistered = userRepository.existsBySocialToken_SocialTokenAndProvider(socialInfo.socialId(), socialType);

        if (!isRegistered) {
            User user = new User(randomNicknameGenerator.generate(), socialType.name(), socialInfo.socialId());
            userRepository.save(user);
        }

        User user = getUserBySocialInfo(socialInfo.socialId(), socialType);

        String accessToken = jwtService.issuedAccessToken(user.getUserId());
        String refreshToken = jwtService.issuedRefreshToken(user.getUserId());

        return SignInResponse.of(isRegistered ? "Login" : "Signup", accessToken, refreshToken);
    }

    private User getUserBySocialInfo(String socialToken, SocialType provider) {
        return userRepository.findBySocialToken_SocialTokenAndProvider(socialToken, provider)
            .orElseThrow(() -> new UserNotFoundException(ErrorCode.NOT_FOUND_USER_EXCEPTION, ErrorCode.NOT_FOUND_USER_EXCEPTION.getMessage()));
    }
}
