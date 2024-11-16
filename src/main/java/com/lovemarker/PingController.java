package com.lovemarker;

import com.lovemarker.global.constant.SuccessCode;
import com.lovemarker.global.dto.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ping")
@RequiredArgsConstructor
public class PingController {
    @GetMapping
    public ApiResponseDto<String> ping() {
        return ApiResponseDto.success(SuccessCode.PING_SUCCESS, "pong");
    }
}
