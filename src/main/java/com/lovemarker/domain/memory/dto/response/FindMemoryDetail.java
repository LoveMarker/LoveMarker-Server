package com.lovemarker.domain.memory.dto.response;

import com.lovemarker.domain.memory.Memory;
import java.time.LocalDate;
import java.util.List;

public record FindMemoryDetail(
    Long memoryId,
    String title,
    String content,
    LocalDate date,
    String address,
    String writer,
    Boolean isWriter,
    List<String> images
) {
    public static FindMemoryDetail of(Memory memory, Boolean isWriter) {
        return new FindMemoryDetail(memory.getMemoryId(), memory.getTitle(), memory.getContent(),
            memory.getDate(), memory.getAddressInfo().getAddress(), memory.getUser().getNickname(),
            isWriter, memory.getImages());
    }
}
