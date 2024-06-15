package com.lovemarker.domain.memory.service;

import com.lovemarker.domain.couple.Couple;
import com.lovemarker.domain.memory.Memory;
import com.lovemarker.domain.memory.dto.response.CreateMemoryResponse;
import com.lovemarker.domain.memory.dto.response.FindMemoryByRadiusResponse;
import com.lovemarker.domain.memory.dto.response.FindMemoryDetail;
import com.lovemarker.domain.memory.dto.response.FindMemoryListResponse;
import com.lovemarker.domain.memory.exception.MemoryNotFoundException;
import com.lovemarker.domain.memory.repository.MemoryRepository;
import com.lovemarker.domain.user.User;
import com.lovemarker.domain.user.exception.UserNotFoundException;
import com.lovemarker.domain.user.repository.UserRepository;
import com.lovemarker.global.aspect.CouplePermissionCheck;
import com.lovemarker.global.constant.ErrorCode;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemoryService {

    private final MemoryRepository memoryRepository;
    private final UserRepository userRepository;
    private final GeometryFactory geometryFactory;

    @Transactional
    @CouplePermissionCheck
    public CreateMemoryResponse createMemory(Long userId, LocalDate date, String title, String content,
        Double latitude, Double longitude, String address, List<String> images
    ) {
        User user = getUserByUserId(userId);
        Memory memory = new Memory(date, title, content, address, getPoint(latitude, longitude), user, images);
        memoryRepository.save(memory);
        return CreateMemoryResponse.of(memory.getMemoryId());
    }

    @Transactional(readOnly = true)
    public FindMemoryDetail findMemoryDetail(Long userId, Long memoryId) {
        Memory memory = getMemoryByMemoryId(memoryId);
        return FindMemoryDetail.of(memory, Objects.equals(memory.getUser().getUserId(), userId));
    }

    @Transactional(readOnly = true)
    @CouplePermissionCheck
    public FindMemoryListResponse findMemoryList(Long userId, int page, int size) {
        User user = getUserByUserId(userId);
        Couple couple = user.getCouple();
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Memory> memories = memoryRepository.findByCouple_CoupleIdOrderByCreatedAtDesc(
            couple.getCoupleId(), pageRequest);
        return FindMemoryListResponse.from(memories);
    }

    @Transactional(readOnly = true)
    public FindMemoryListResponse findMyMemoryList(Long userId, int page, int size) {
        User user = getUserByUserId(userId);
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Memory> memories = memoryRepository.findByUser_UserIdOrderByCreatedAtDesc(
            user.getUserId(), pageRequest
        );
        return FindMemoryListResponse.from(memories);
    }

    @Transactional(readOnly = true)
    @CouplePermissionCheck
    public FindMemoryByRadiusResponse findMemoryByRadius(
        Long userId, int radius, Double latitude, Double longitude
    ) {
        User user = getUserByUserId(userId);
        Couple couple = user.getCouple();
        List<Memory> memoryList = memoryRepository
            .findByRadius(couple.getCoupleId(), getPoint(latitude, longitude), radius);
        List<FindMemoryByRadiusResponse.FindMemoryResponse> memoryResponses = memoryList.stream()
            .map(memory -> FindMemoryByRadiusResponse.FindMemoryResponse.of(memory.getMemoryId(),
                memory.getAddressInfo().getPosition().getX(), memory.getAddressInfo().getPosition().getY()))
            .collect(Collectors.toList());
        return FindMemoryByRadiusResponse.of(memoryResponses);
    }

    private User getUserByUserId(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(ErrorCode.NOT_FOUND_USER_EXCEPTION,
                ErrorCode.NOT_FOUND_USER_EXCEPTION.getMessage()));
    }

    private Memory getMemoryByMemoryId(Long memoryId) {
        return memoryRepository.findById(memoryId)
            .orElseThrow(() -> new MemoryNotFoundException(ErrorCode.NOT_FOUND_MEMORY_EXCEPTION,
                ErrorCode.NOT_FOUND_MEMORY_EXCEPTION.getMessage()));
    }

    private Point getPoint(Double latitude, Double longitude) {
        return geometryFactory.createPoint(new Coordinate(latitude, longitude));
    }
}
