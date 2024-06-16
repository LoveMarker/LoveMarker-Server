package com.lovemarker.domain.auth.service;

import com.lovemarker.domain.auth.RandomNicknameGenerator;
import com.lovemarker.domain.auth.dto.response.ReissueTokenResponse;
import com.lovemarker.domain.auth.dto.response.SignInResponse;
import com.lovemarker.domain.auth.dto.response.SocialInfoResponse;
import com.lovemarker.domain.auth.exception.InvalidTokenException;
import com.lovemarker.domain.auth.exception.TimeExpiredTokenException;
import com.lovemarker.domain.user.User;
import com.lovemarker.domain.user.exception.UserNotFoundException;
import com.lovemarker.domain.user.repository.UserRepository;
import com.lovemarker.domain.user.vo.SocialType;
import com.lovemarker.global.constant.ErrorCode;
import com.lovemarker.global.constant.TokenStatus;
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
    private final TokenCacheService tokenCacheService;

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

    @Transactional
    public ReissueTokenResponse reissueToken(String refreshToken) {
        if (jwtService.verifyToken(refreshToken) == TokenStatus.TOKEN_INVALID) {
            throw new InvalidTokenException(ErrorCode.INVALID_REFRESH_TOKEN_EXCEPTION,
                ErrorCode.INVALID_REFRESH_TOKEN_EXCEPTION.getMessage());
        } else if (jwtService.verifyToken(refreshToken) == TokenStatus.TOKEN_EXPIRED) {
            throw new TimeExpiredTokenException(ErrorCode.REFRESH_TOKEN_TIME_EXPIRED_EXCEPTION,
                ErrorCode.REFRESH_TOKEN_TIME_EXPIRED_EXCEPTION.getMessage());
        }

        final String tokenContents = jwtService.getJwtContents(refreshToken);
        try {
            final long userId =  Long.parseLong(tokenContents);
            if (tokenCacheService.getValuesByKey(String.valueOf(userId)).isBlank()) {
                throw new InvalidTokenException(ErrorCode.INVALID_REFRESH_TOKEN_EXCEPTION,
                    ErrorCode.INVALID_REFRESH_TOKEN_EXCEPTION.getMessage());
            }
            User user = getUserByUserId(userId);
            String newAccessToken = jwtService.issuedAccessToken(user.getUserId());
            return new ReissueTokenResponse(newAccessToken);

        } catch (NumberFormatException e) {
            throw new UserNotFoundException(ErrorCode.NOT_FOUND_USER_EXCEPTION,
                ErrorCode.NOT_FOUND_USER_EXCEPTION.getMessage());
        } catch (NullPointerException e) {
            throw new InvalidTokenException(ErrorCode.INVALID_REFRESH_TOKEN_EXCEPTION,
                ErrorCode.INVALID_REFRESH_TOKEN_EXCEPTION.getMessage());
        }
    }

    private User getUserBySocialInfo(String socialToken, SocialType provider) {
        return userRepository.findBySocialToken_SocialTokenAndProvider(socialToken, provider)
            .orElseThrow(() -> new UserNotFoundException(ErrorCode.NOT_FOUND_USER_EXCEPTION, ErrorCode.NOT_FOUND_USER_EXCEPTION.getMessage()));
    }

    private User getUserByUserId(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(ErrorCode.NOT_FOUND_USER_EXCEPTION,
                ErrorCode.NOT_FOUND_USER_EXCEPTION.getMessage()));
    }
}
