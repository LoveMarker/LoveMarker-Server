package com.lovemarker.domain.user.controller;

import com.lovemarker.domain.user.dto.FindMyPageResponse;
import com.lovemarker.domain.user.dto.request.UpdateUserNicknameRequest;
import com.lovemarker.domain.user.service.UserService;
import com.lovemarker.global.config.resolver.UserId;
import com.lovemarker.global.constant.SuccessCode;
import com.lovemarker.global.dto.ApiResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ApiResponseDto<FindMyPageResponse> findMyPage(
        @UserId Long userId
    ) {
        return ApiResponseDto.success(SuccessCode.FIND_MY_PAGE_SUCCESS, userService.findMyPage(userId));
    }

    @PatchMapping("/nickname")
    public ApiResponseDto updateUserNickname(
        @UserId Long userId,
        @Valid @RequestBody final UpdateUserNicknameRequest updateUserNicknameRequest
    ) {
        userService.updateUserNickname(userId, updateUserNicknameRequest.nickname());
        return ApiResponseDto.success(SuccessCode.UPDATE_USER_NICKNAME_SUCCESS);
    }
}
