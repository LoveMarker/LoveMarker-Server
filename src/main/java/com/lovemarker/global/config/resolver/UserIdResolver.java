package com.lovemarker.global.config.resolver;

import com.lovemarker.domain.auth.service.JwtService;
import com.lovemarker.domain.user.exception.InvalidAccessTokenException;
import com.lovemarker.domain.user.exception.NullAccessTokenException;
import com.lovemarker.domain.user.exception.TimeExpiredAccessTokenException;
import com.lovemarker.domain.user.exception.UserNotFoundException;
import com.lovemarker.global.constant.ErrorCode;
import com.lovemarker.global.constant.TokenStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class UserIdResolver implements HandlerMethodArgumentResolver {
    private final JwtService jwtService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(UserId.class) && Long.class.equals(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(@NotNull MethodParameter parameter, ModelAndViewContainer modelAndViewContainer, @NotNull NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        final HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String accessToken = request.getHeader("accessToken");

        if (accessToken == null) {
            throw new NullAccessTokenException(ErrorCode.NULL_ACCESS_TOKEN_EXCEPTION, ErrorCode.NULL_ACCESS_TOKEN_EXCEPTION.getMessage());
        }

        // 토큰 검증
        if (jwtService.verifyToken(accessToken) == TokenStatus.TOKEN_EXPIRED) {
            throw new TimeExpiredAccessTokenException(ErrorCode.ACCESS_TOKEN_TIME_EXPIRED_EXCEPTION, ErrorCode.ACCESS_TOKEN_TIME_EXPIRED_EXCEPTION.getMessage());
        } else if (jwtService.verifyToken(accessToken) == TokenStatus.TOKEN_INVALID) {
            throw new InvalidAccessTokenException(ErrorCode.INVALID_ACCESS_TOKEN_EXCEPTION, ErrorCode.INVALID_ACCESS_TOKEN_EXCEPTION.getMessage());
        }

        // 유저 아이디 반환
        final String tokenContents = jwtService.getJwtContents(accessToken);

        try {
            return Long.parseLong(tokenContents);
        } catch (NumberFormatException e) {
            throw new UserNotFoundException(ErrorCode.NOT_FOUND_USER_EXCEPTION, ErrorCode.NOT_FOUND_USER_EXCEPTION.getMessage());
        }
    }
}
