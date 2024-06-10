package com.lovemarker.domain.memory.service;

import com.lovemarker.domain.couple.Couple;
import com.lovemarker.domain.memory.Memory;
import com.lovemarker.domain.memory.dto.response.CreateMemoryResponse;
import com.lovemarker.domain.memory.repository.MemoryRepository;
import com.lovemarker.domain.user.User;
import com.lovemarker.domain.user.exception.UserNotFoundException;
import com.lovemarker.domain.user.repository.UserRepository;
import com.lovemarker.global.constant.ErrorCode;
import com.lovemarker.global.exception.ForbiddenException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemoryService {

    private final MemoryRepository memoryRepository;
    private final UserRepository userRepository;
    private final GeometryFactory geometryFactory;

    @Transactional
    public CreateMemoryResponse createMemory(Long userId, LocalDate date, String title, String content,
        Double latitude, Double longitude, String address, List<String> images
    ) {
        User user = getUserByUserId(userId);
        validateCoupleConnection(user);
        Memory memory = new Memory(date, title, content, address, getPoint(latitude, longitude), user, images);
        return CreateMemoryResponse.of(memoryRepository.save(memory).getMemoryId());
    }

    private void validateCoupleConnection(User user) {
        Couple couple = user.getCouple();
        if (Objects.isNull(couple) || userRepository.countByCouple_CoupleId(couple.getCoupleId()) < 2) {
            throw new ForbiddenException(ErrorCode.NO_COUPLE_FORBIDDEN_EXCEPTION,
                ErrorCode.NO_COUPLE_FORBIDDEN_EXCEPTION.getMessage());
        }
    }

    private User getUserByUserId(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(ErrorCode.NOT_FOUND_EXCEPTION, "존재하지 않는 유저입니다."));
    }

    private Point getPoint(Double latitude, Double longitude) {
        return geometryFactory.createPoint(new Coordinate(latitude, longitude));
    }
}
