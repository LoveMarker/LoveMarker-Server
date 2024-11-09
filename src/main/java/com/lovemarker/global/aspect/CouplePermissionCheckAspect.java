package com.lovemarker.global.aspect;

import com.lovemarker.domain.couple.Couple;
import com.lovemarker.domain.user.User;
import com.lovemarker.domain.user.exception.UserNotFoundException;
import com.lovemarker.domain.user.repository.UserRepository;
import com.lovemarker.global.constant.ErrorCode;
import com.lovemarker.global.exception.ForbiddenException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class CouplePermissionCheckAspect {

    private final UserRepository userRepository;

    @Before("@annotation(CouplePermissionCheck) && args(userId,..)")
    public void validateCoupleConnection(JoinPoint joinPoint, Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(ErrorCode.NOT_FOUND_EXCEPTION, "존재하지 않는 유저입니다."));
        Couple couple = user.getCouple();
        if (Objects.isNull(couple) || userRepository.countByCouple_CoupleId(couple.getCoupleId()) < 2) {
            throw new ForbiddenException(ErrorCode.NO_COUPLE_FORBIDDEN_EXCEPTION,
                ErrorCode.NO_COUPLE_FORBIDDEN_EXCEPTION.getMessage());
        }
    }
}
