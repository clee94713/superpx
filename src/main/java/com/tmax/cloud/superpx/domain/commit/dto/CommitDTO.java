package com.tmax.cloud.superpx.domain.commit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class CommitDTO {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Get {
        Long id;
        String message;
        Long authorId;
    }
}
