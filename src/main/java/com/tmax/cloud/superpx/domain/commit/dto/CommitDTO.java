package com.tmax.cloud.superpx.domain.commit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class CommitDTO {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommitCreate {
        Long projectId;
        Long referenceId;
        String message;
        Long authorId;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommitGet {
        Long id;
        String message;
        Long authorId;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommitRevert {
        Long revertId;
        Long referenceId;
    }
}
