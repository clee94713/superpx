package com.tmax.cloud.superpx.domain.root.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class SampleDTO {
    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @Builder
    public static class Create {
        @Schema(description = "사용자 ID", hidden = true)
        String uuid;
        @Schema(description = "사용자 이름", nullable = false, example = "홍길동")
        String name;
        @Schema(description = "사용자 성별", example = "남자", allowableValues = {"남자", "여자"})
        String gender;
        @Schema(description = "비밀번호", example = "abcd1234")
        String password;
        @DateTimeFormat(pattern = "yyyyMMdd")
        @Schema(description = "생년월일", example = "yyyyMMdd", maxLength = 8)
        String birthDate;
    }

    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString(callSuper = true)
    public static class Get extends Create {
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd kk:mm:ss", timezone = "Asia/Seoul")
        @Schema(description = "생성 일시")
        LocalDateTime createdDate;
    }
}
