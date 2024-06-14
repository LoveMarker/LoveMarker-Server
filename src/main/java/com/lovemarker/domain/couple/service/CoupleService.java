package com.lovemarker.domain.couple.service;

import com.lovemarker.domain.couple.Couple;
import com.lovemarker.domain.couple.InviteCodeStrategy;
import com.lovemarker.domain.couple.dto.response.CreateInvitationCodeResponse;
import com.lovemarker.domain.couple.repository.CoupleRepository;
import com.lovemarker.domain.user.User;
import com.lovemarker.domain.user.exception.UserNotFoundException;
import com.lovemarker.domain.user.repository.UserRepository;
import com.lovemarker.global.aspect.CouplePermissionCheck;
import com.lovemarker.global.constant.ErrorCode;
import com.lovemarker.global.exception.BadRequestException;
import com.lovemarker.global.exception.NotFoundException;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CoupleService {

    private final CoupleRepository coupleRepository;
    private final UserRepository userRepository;
    private final InviteCodeStrategy inviteCodeStrategy;

    @Transactional
    public CreateInvitationCodeResponse createInvitationCode(Long userId, LocalDate anniversary) {
        User user = getUserByUserId(userId);
        Couple couple = new Couple(anniversary, getNewInvitationCode());
        user.connectCouple(couple);
        coupleRepository.save(couple);
        return CreateInvitationCodeResponse.from(couple);
    }

    @Transactional
    public void joinCouple(Long userId, String invitationCode) {
        User user = getUserByUserId(userId);
        Couple couple = getCoupleByInviteCode(invitationCode);
        validateJoinCouple(couple.getCoupleId());
        user.connectCouple(couple);
    }

    @Transactional
    @CouplePermissionCheck
    public void disconnectCouple(Long userId) {
        User user = getUserByUserId(userId);
        user.disconnectCouple();
    }

    private User getUserByUserId(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(ErrorCode.NOT_FOUND_EXCEPTION, "존재하지 않는 유저입니다."));
    }

    private void validateJoinCouple(Long coupleId) {
        if (userRepository.countByCouple_CoupleId(coupleId) >= 2) {
            throw new BadRequestException(ErrorCode.REQUEST_VALIDATION_EXCEPTION, "연결이 완료된 커플입니다.");
        }
    }

    private Couple getCoupleByInviteCode(String inviteCode) {
        return coupleRepository.findByInviteCode(inviteCode)
            .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_EXCEPTION, "존재하지 않는 커플입니다."));
    }

    private String getNewInvitationCode() {
        return inviteCodeStrategy.generate();
    }
}
