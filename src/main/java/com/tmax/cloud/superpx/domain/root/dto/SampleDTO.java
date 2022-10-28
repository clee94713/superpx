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
        @Schema(description = "����� ID", hidden = true)
        String uuid;
        @Schema(description = "����� �̸�", nullable = false, example = "ȫ�浿")
        String name;
        @Schema(description = "����� ����", example = "����", allowableValues = {"����", "����"})
        String gender;
        @Schema(description = "��й�ȣ", example = "abcd1234")
        String password;
        @DateTimeFormat(pattern = "yyyyMMdd")
        @Schema(description = "�������", example = "yyyyMMdd", maxLength = 8)
        String birthDate;
    }

    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString(callSuper = true)
    public static class Get extends Create {
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd kk:mm:ss", timezone = "Asia/Seoul")
        @Schema(description = "���� �Ͻ�")
        LocalDateTime createdDate;
    }
}
