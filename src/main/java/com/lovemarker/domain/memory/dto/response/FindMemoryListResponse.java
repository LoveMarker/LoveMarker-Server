package com.lovemarker.domain.memory.dto.response;

import com.lovemarker.domain.memory.Memory;
import com.lovemarker.global.dto.PageInfo;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;

public record FindMemoryListResponse(
    PageInfo pageInfo,
    List<FindMemoryResponse> memories
) {

    public record FindMemoryResponse(
        Long memoryId,
        String title,
        LocalDate date,
        String address,
        String image
    ) {
        public static FindMemoryResponse from(Memory memory) {
            return new FindMemoryResponse(memory.getMemoryId(), memory.getTitle(), memory.getDate(),
                memory.getAddressInfo().getAddress(), memory.getImages().get(0));
        }
    }

    public static FindMemoryListResponse from(Page<Memory> memories) {
        PageInfo pageInfo = PageInfo.of(memories.getTotalElements(), memories.hasNext());
        List<FindMemoryResponse> findMemoryResponses = memories.get()
            .map(FindMemoryResponse::from).toList();
        return new FindMemoryListResponse(pageInfo, findMemoryResponses);
    }
}
