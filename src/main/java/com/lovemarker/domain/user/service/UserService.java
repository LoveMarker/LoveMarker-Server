package com.lovemarker.domain.user.service;

import com.lovemarker.domain.couple.Couple;
import com.lovemarker.domain.user.User;
import com.lovemarker.domain.user.dto.FindMyPageResponse;
import com.lovemarker.domain.user.exception.UserNicknameDuplicateException;
import com.lovemarker.domain.user.exception.UserNotFoundException;
import com.lovemarker.domain.user.repository.UserRepository;
import com.lovemarker.global.constant.ErrorCode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void updateUserNickname(Long userId, String nickname) {
        checkDuplicatedNickname(nickname);
        getUserByUserId(userId).updateUserNickname(nickname);
    }

    @Transactional(readOnly = true)
    public FindMyPageResponse findMyPage(Long userId) {
        User user = getUserByUserId(userId);
        Couple couple = user.getCouple();

        // 커플 데이터가 존재하지 않는 경우
        if (Objects.isNull(couple)) {
            return FindMyPageResponse.of(false, user.getNickname());
        }

        long anniversaryDays = Math.abs(ChronoUnit.DAYS.between(couple.getAnniversary(), LocalDateTime.now())) + 1;

        // 상대방이 아직 수락하지 않은 경우
        if (userRepository.countByCouple_CoupleId(couple.getCoupleId()) < 2) {
            return FindMyPageResponse.of(false, user.getNickname(), anniversaryDays);
        }

        User partner = findPartner(user.getUserId(), couple.getCoupleId());
        return FindMyPageResponse.of(true, user.getNickname(), anniversaryDays, partner.getNickname());
    }

    private User getUserByUserId(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(ErrorCode.NOT_FOUND_USER_EXCEPTION, "존재하지 않는 유저입니다."));
    }

    private void checkDuplicatedNickname(String nickname) {
        if (userRepository.existsByNickname_Nickname(nickname)) {
            throw new UserNicknameDuplicateException(ErrorCode.DUPLICATE_NICKNAME_EXCEPTION,
                ErrorCode.DUPLICATE_NICKNAME_EXCEPTION.getMessage());
        }
    }

    private User findPartner(Long userId, Long coupleId) {
        return userRepository.findPartnerUser(coupleId, userId)
            .orElseThrow(() -> new UserNotFoundException(ErrorCode.NOT_FOUND_USER_EXCEPTION, "상대방 유저 데이터가 존재하지 않습니다."));
    }
}
