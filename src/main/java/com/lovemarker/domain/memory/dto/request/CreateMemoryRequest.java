package com.lovemarker.domain.memory.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public record CreateMemoryRequest(
    @NotNull(message = "날짜는 필수 입력 항목입니다.") LocalDate date,
    @NotBlank(message = "제목은 필수 입력 항목입니다.") String title,
    @NotBlank(message = "내용은 필수 입력 항목입니다.") String content,
    @NotNull(message = "좌표는 필수 입력 항목입니다.") Double latitude,
    @NotNull(message = "좌표는 필수 입력 항목입니다.") Double longitude,
    @NotBlank(message = "주소는 필수 입력 항목입니다.") String address,
    @NotNull(message = "이미지는 필수 입력 항목입니다.")
    @Size(min = 1, max = 5, message = "이미지는 1개 이상 5개 이하로 선택하세요.")
    List<MultipartFile> images
) {

}
