package com.lovemarker.domain.auth.controller;

import com.lovemarker.domain.auth.dto.request.SignInRequest;
import com.lovemarker.domain.auth.dto.response.ReissueTokenResponse;
import com.lovemarker.domain.auth.dto.response.SignInResponse;
import com.lovemarker.domain.auth.service.AuthService;
import com.lovemarker.global.constant.SuccessCode;
import com.lovemarker.global.dto.ApiResponseDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;

    @PostMapping
    public ApiResponseDto<SignInResponse> signIn(
        @RequestHeader @NotBlank String token,
        @Valid @RequestBody final SignInRequest signInRequest
    ) {
        return ApiResponseDto.success(SuccessCode.LOGIN_SUCCESS,
            authService.signIn(token, signInRequest.provider()));
    }

    @GetMapping("/reissue-token")
    public ApiResponseDto<ReissueTokenResponse> reissueToken(
        @RequestHeader @NotBlank String refreshToken
    ) {
        return ApiResponseDto.success(SuccessCode.REISSUE_TOKEN_SUCCESS,
            authService.reissueToken(refreshToken));
    }
}
