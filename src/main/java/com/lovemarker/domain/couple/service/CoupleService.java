package com.lovemarker.domain.couple.service;

import com.lovemarker.domain.couple.Couple;
import com.lovemarker.domain.couple.InviteCodeStrategy;
import com.lovemarker.domain.couple.dto.response.CreateInvitationCodeResponse;
import com.lovemarker.domain.couple.repository.CoupleRepository;
import com.lovemarker.domain.user.User;
import com.lovemarker.domain.user.repository.UserRepository;
import com.lovemarker.global.constant.ErrorCode;
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

    private User getUserByUserId(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_EXCEPTION, "존재하지 않는 유저입니다."));
    }

    private String getNewInvitationCode() {
        return inviteCodeStrategy.generate();
    }
}
