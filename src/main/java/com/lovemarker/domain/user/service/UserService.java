package com.lovemarker.domain.user.service;

import com.lovemarker.domain.user.User;
import com.lovemarker.domain.user.exception.UserNicknameDuplicateException;
import com.lovemarker.domain.user.repository.UserRepository;
import com.lovemarker.global.constant.ErrorCode;
import com.lovemarker.global.exception.NotFoundException;
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

    private User getUserByUserId(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_EXCEPTION, "존재하지 않는 유저입니다."));
    }

    private void checkDuplicatedNickname(String nickname) {
        if (userRepository.existsByNickname_Nickname(nickname)) {
            throw new UserNicknameDuplicateException(ErrorCode.DUPLICATE_NICKNAME_EXCEPTION,
                ErrorCode.DUPLICATE_NICKNAME_EXCEPTION.getMessage());
        }
    }
}
