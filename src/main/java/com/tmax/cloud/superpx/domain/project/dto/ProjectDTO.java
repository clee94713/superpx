package com.tmax.cloud.superpx.domain.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ProjectDTO {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProjectGet {
        Long id;
        String name;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProjectCreate {
        String name;
    }
}
