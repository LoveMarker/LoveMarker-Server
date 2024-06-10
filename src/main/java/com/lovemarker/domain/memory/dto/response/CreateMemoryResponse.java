package com.lovemarker.domain.memory.dto.response;

public record CreateMemoryResponse(
    Long memoryId
) {
    public static CreateMemoryResponse of(Long memoryId) {
        return new CreateMemoryResponse(memoryId);
    }
}
