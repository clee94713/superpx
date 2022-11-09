package com.tmax.cloud.superpx.domain.reference.dto;

import com.tmax.cloud.superpx.data.primary.model.enums.ReferenceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ReferenceDTO {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Create {
        Long projectId;
        String name;
        ReferenceType type;
        Long currentReferenceId;
        Long currentCommitId;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Get {
        Long id;
        String name;
        ReferenceType type;
    }
}
