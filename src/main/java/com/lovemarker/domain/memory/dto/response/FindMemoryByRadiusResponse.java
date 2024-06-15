package com.lovemarker.domain.memory.dto.response;

import java.util.List;

public record FindMemoryByRadiusResponse(
    List<FindMemoryResponse> memories
) {

    public record FindMemoryResponse(
        Long memoryId,
        Double latitude,
        Double longitude
    ) {
        public static FindMemoryResponse of(Long memoryId, Double latitude, Double longitude) {
            return new FindMemoryResponse(memoryId, latitude, longitude);
        }
    }

    public static FindMemoryByRadiusResponse of(List<FindMemoryResponse> memories) {
        return new FindMemoryByRadiusResponse(memories);
    }
}
