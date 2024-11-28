package com.lovemarker.domain.memory.controller;

import com.lovemarker.domain.memory.dto.request.CreateMemoryRequest;
import com.lovemarker.domain.memory.dto.response.CreateMemoryResponse;
import com.lovemarker.domain.memory.dto.response.FindMemoryByRadiusResponse;
import com.lovemarker.domain.memory.dto.response.FindMemoryDetail;
import com.lovemarker.domain.memory.dto.response.FindMemoryListResponse;
import com.lovemarker.domain.memory.service.MemoryService;
import com.lovemarker.global.config.resolver.UserId;
import com.lovemarker.global.constant.SuccessCode;
import com.lovemarker.global.dto.ApiResponseDto;
import com.lovemarker.global.image.S3Service;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/memory")
@RequiredArgsConstructor
public class MemoryController {

    private final MemoryService memoryService;
    private final S3Service s3Service;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponseDto<CreateMemoryResponse> createMemory(
        @UserId Long userId, @ModelAttribute @Valid CreateMemoryRequest createMemoryRequest
    ) {
        List<String> urls = s3Service.uploadImages(List.of(createMemoryRequest.images()));
        return ApiResponseDto.success(SuccessCode.CREATE_MEMORY_SUCCESS, memoryService.createMemory(
           userId, createMemoryRequest.date(), createMemoryRequest.title(), createMemoryRequest.content(),
            createMemoryRequest.latitude(), createMemoryRequest.longitude(),  createMemoryRequest.address(),
            urls
        ));
    }

    @GetMapping("/{memoryId}")
    public ApiResponseDto<FindMemoryDetail> findMemoryDetail(
        @UserId Long userId, @PathVariable Long memoryId
    ) {
        return ApiResponseDto.success(SuccessCode.FIND_MEMORY_DETAIL_SUCCESS,
            memoryService.findMemoryDetail(userId, memoryId));
    }

    @GetMapping("/list-view")
    public ApiResponseDto<FindMemoryListResponse> findMemoryList(
        @UserId Long userId,
        @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size
    ) {
        return ApiResponseDto.success(SuccessCode.FIND_MEMORY_LIST_SUCCESS,
            memoryService.findMemoryList(userId, page == null ? 0 : page, size == null ? 10 : size));
    }

    @GetMapping("/me")
    public ApiResponseDto<FindMemoryListResponse> findMyMemoryList(
        @UserId Long userId,
        @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size
    ) {
        return ApiResponseDto.success(SuccessCode.FIND_MY_MEMORY_LIST_SUCCESS,
            memoryService.findMyMemoryList(userId, page == null ? 0 : page, size == null ? 10 : size));
    }

    @GetMapping("/map-view")
    public ApiResponseDto<FindMemoryByRadiusResponse> findMemoryByRadius(
        @UserId Long userId, @RequestParam(required = false) Double radius,
        @RequestParam Double latitude, @RequestParam Double longitude
    ) {
        return ApiResponseDto.success(SuccessCode.FIND_MEMORY_MAP_VIEW_SUCCESS,
            memoryService.findMemoryByRadius(userId, radius == null ? 3000 : radius, latitude, longitude));
    }
}
