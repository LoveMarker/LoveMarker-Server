package com.lovemarker.domain.couple.controller;

import com.lovemarker.domain.couple.dto.request.CreateInvitationCodeRequest;
import com.lovemarker.domain.couple.dto.request.JoinCoupleRequest;
import com.lovemarker.domain.couple.dto.response.CreateInvitationCodeResponse;
import com.lovemarker.domain.couple.service.CoupleService;
import com.lovemarker.global.constant.SuccessCode;
import com.lovemarker.global.dto.ApiResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/couple")
@RequiredArgsConstructor
public class CoupleController {
    private final CoupleService coupleService;

    @PostMapping
    public ApiResponseDto<CreateInvitationCodeResponse> createInvitationCode(
        @RequestHeader Long userId,
        @Valid @RequestBody final CreateInvitationCodeRequest createInvitationCodeRequest
    ) {
        return ApiResponseDto.success(SuccessCode.CREATE_INVITATION_CODE_SUCCESS,
            coupleService.createInvitationCode(userId, createInvitationCodeRequest.anniversary()));
    }

    @PostMapping("/join")
    public ApiResponseDto joinCouple(
        @RequestHeader Long userId,
        @Valid @RequestBody final JoinCoupleRequest joinCoupleRequest
    ) {
        coupleService.joinCouple(userId, joinCoupleRequest.invitationCode());
        return ApiResponseDto.success(SuccessCode.JOIN_COUPLE_SUCCESS);
    }
}
